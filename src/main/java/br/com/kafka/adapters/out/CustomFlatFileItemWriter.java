package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
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
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.StepContext;
import java.util.Iterator;
import java.util.List;
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

        while(var3.hasNext()) {
            Cliente item = (Cliente) var3.next();

            PutItemEnhancedRequest<Cliente> putItemEnhancedRequest = PutItemEnhancedRequest
                    .builder(Cliente.class)
                    .item(item)
                    .build();

            CompletableFuture<PutItemEnhancedResponse<Cliente>> completableFuture = dynamoDbEnhancedAsyncClient.table("tbb001_clientes", TableSchema.fromBean(Cliente.class))
                    .putItemWithResponse(putItemEnhancedRequest);

            PutItemEnhancedResponse<Cliente> response = completableFuture.join();

            lines.append(this.lineAggregator.aggregate(item)).append(this.lineSeparator);
            updateCount++;
        }

        return lines.toString();
    }

    public int getUpdateCount() {
        return updateCount;
    }

    @AfterStep
    public void afterStep() {
        System.out.println("UPDATE COUNT: " + updateCount);
    }

}


