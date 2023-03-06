package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClientExtension;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.MappedTableResource;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class CustomMappedTableResource implements MappedTableResource<Cliente> {
    @Override
    public DynamoDbEnhancedClientExtension mapperExtension() {
        return null;
    }

    @Override
    public TableSchema<Cliente> tableSchema() {
        return TableSchema.fromBean(Cliente.class);
    }

    @Override
    public String tableName() {
        return "tbb001_clientes";
    }

    @Override
    public Key keyFrom(Cliente cliente) {
        return Key.builder().partitionValue("id").sortValue("name").build();
    }
}
