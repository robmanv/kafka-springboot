package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import feign.Client;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.MappedTableResource;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.internal.client.DefaultDynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.Put;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItem;
import software.amazon.awssdk.services.dynamodb.model.TransactWriteItemsResponse;

import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.StepContext;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CustomFlatFileItemWriter extends FlatFileItemWriter<Cliente> {
    @Autowired
    ExecutionContext executionContext;

    private int updateCount = 0;

    @Autowired
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    public CustomFlatFileItemWriter(Resource resource) {
        setResource(new FileSystemResource("clientes.txt"));
        setAppendAllowed(true);
        setEncoding("UTF-8");
        setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(";");
            setFieldExtractor(new BeanWrapperFieldExtractor<>(){{
                setNames(new String[]{"id", "name", "age", "height", "weight", "salary", "address"});
            }});
        }});
    }

    public String doWrite(List<? extends Cliente> items) {
        StringBuilder lines = new StringBuilder();
        Iterator var3 = items.iterator();

        TransactWriteItemsEnhancedRequest.Builder transactWriteItemsEnhancedRequest = TransactWriteItemsEnhancedRequest.builder();

        List<TransactWriteItem> listTransactWriteItem = new ArrayList<>();
        List<Cliente> listCliente = new ArrayList<>();
        List<WriteBatch> listWriteBatch = new ArrayList<>();
//        List<TransactWriteItemsEnhancedRequest> listTransactWriteItemEnhancedRequest = new ArrayList<>();

        while(var3.hasNext()) {
            Cliente item = (Cliente) var3.next();

            PutItemEnhancedRequest<Cliente> putItemEnhancedRequest = PutItemEnhancedRequest
                    .builder(Cliente.class)
                    .item(item)
                    .build();

            listTransactWriteItem.add(TransactWriteItem.builder()
                    .put(Put.builder()
                            .tableName("tbb001_clientes")
                            .item(getMapAttributeValue(item))
                            .build())
                    .build());

            listCliente.add(item);
            listWriteBatch.add(WriteBatch.builder(Cliente.class).mappedTableResource(new CustomMappedTableResource()).addPutItem(item).build());

//            listTransactWriteItemEnhancedRequest.add(TransactWriteItemsEnhancedRequest.builder().addPutItem(new CustomMappedTableResource(), item).build());

//            transactWriteItemsEnhancedRequest.transactWriteItems().add(transactWriteItem);

//            TransactPutItemEnhancedRequest<Cliente> transactPutItemEnhancedRequest = TransactPutItemEnhancedRequest
//                    .builder(Cliente.class)
//                    .item(item)
//                    .build();
//
//            List<TransactPutItemEnhancedRequest> transactWriteItems = new ArrayList<>();
//            transactWriteItems.add(transactPutItemEnhancedRequest);

            transactWriteItemsEnhancedRequest.addPutItem(new CustomMappedTableResource(), item);

//            CompletableFuture<PutItemEnhancedResponse<Cliente>> completableFuture = dynamoDbEnhancedAsyncClient
//                    .table("tbb001_clientes", TableSchema.fromBean(Cliente.class))
//                    .putItemWithResponse(putItemEnhancedRequest);
//
//            PutItemEnhancedResponse<Cliente> response = completableFuture.join();

            lines.append(this.lineAggregator.aggregate(item)).append(this.lineSeparator);
            updateCount++;
        }


//        for (int i = 0; i < listWriteBatch.size(); i += 25) {
//            List<WriteBatch> batch = listWriteBatch.subList(i, Math.min(i + 25, listWriteBatch.size()));
            BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
                    .writeBatches(listWriteBatch.subList(1, 25))
                    .build();
//            dynamoDbEnhancedAsyncClient.batchWriteItem(BatchWriteItemEnhancedRequest.builder().addWriteBatch(WriteBatch.builder(Cliente.class).addPutItem(batch.get(i)).build()).build());
//        }

//        dynamoDbEnhancedAsyncClient.batchWriteItem(batchWriteItemEnhancedRequest).whenComplete((response, error) -> {
//                    if (error != null) {
//                        // tratamento de erro
//                        System.out.printf("error" + error);
//                    } else {
//                        System.out.printf("response" + response);
//                        // verifica se o item foi inserido ou atualizado
////                            List<TransactWriteItemsResponse> responses = response.responses();
////                            for (TransactWriteItemsResponse itemResponse : responses) {
////                                if (itemResponse instanceof TransactPutItems) {
////                                    TransactPutItemResponse putItemResponse = (TransactPutItemResponse) itemResponse;
////                                    // verifica se o item foi inserido ou atualizado
////                                    if (putItemResponse.item() != null) {
////                                        System.out.println("Item inserido com sucesso: " + putItemResponse.item());
////                                    } else {
////                                        System.out.println("Item atualizado com sucesso.");
////                                    }
////                                } else if (itemResponse instanceof TransactDeleteItemEnhancedRequest) {
////                                    // tratamento para operação de exclusão
////                                }
////                            }
////                        }
//                    }});

        CompletableFuture<Void> result =
                dynamoDbEnhancedAsyncClient.transactWriteItems(transactWriteItemsEnhancedRequest.build()).thenApply(x -> { return x;}).whenComplete((response, error) -> {
                    if (error != null) {
                        // tratamento de erro
                        System.out.printf("error" + error);
                    } else {
                        System.out.printf("response" + response);
                        // verifica se o item foi inserido ou atualizado
//                            List<TransactWriteItemsResponse> responses = response.responses();
//                            for (TransactWriteItemsResponse itemResponse : responses) {
//                                if (itemResponse instanceof TransactPutItems) {
//                                    TransactPutItemResponse putItemResponse = (TransactPutItemResponse) itemResponse;
//                                    // verifica se o item foi inserido ou atualizado
//                                    if (putItemResponse.item() != null) {
//                                        System.out.println("Item inserido com sucesso: " + putItemResponse.item());
//                                    } else {
//                                        System.out.println("Item atualizado com sucesso.");
//                                    }
//                                } else if (itemResponse instanceof TransactDeleteItemEnhancedRequest) {
//                                    // tratamento para operação de exclusão
//                                }
//                            }
//                        }
                    }});

        result.join();


        return lines.toString();
    }

    public int getUpdateCount() {
        return updateCount;
    }

    @AfterStep
    public void afterStep() {
        System.out.println("UPDATE COUNT: " + updateCount);
    }

    public Map<String, AttributeValue> getMapAttributeValue(Cliente item) {
        Map<String, AttributeValue> itemValues = new HashMap<>();

        itemValues.put("id", AttributeValue.builder().s(String.valueOf(item.getId())).build());
        itemValues.put("name", AttributeValue.builder().s(String.valueOf(item.getName())).build());
        itemValues.put("age", AttributeValue.builder().s(String.valueOf(item.getAge())).build());
        itemValues.put("height", AttributeValue.builder().s(String.valueOf(item.getHeight())).build());
        itemValues.put("weight", AttributeValue.builder().s(String.valueOf(item.getWeight())).build());
        itemValues.put("salary", AttributeValue.builder().s(String.valueOf(item.getSalary())).build());
        itemValues.put("address", AttributeValue.builder().s(String.valueOf(item.getAddress())).build());

        return itemValues;

    }


}


