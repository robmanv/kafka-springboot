package br.com.kafka.core.entities;

import br.com.kafka.core.utils.CustomIntegerStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.string.IntegerStringConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Cliente {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbConvertedBy(CustomIntegerStringConverter.class)}))
    private Integer id;   //id

    @Getter(onMethod = @__({@DynamoDbSortKey}))
    private String name; //nome

    @Getter(onMethod = @__({@DynamoDbAttribute(value = "clientAge")}))
    private Integer age; //idade

    @Getter(onMethod = @__({@DynamoDbAttribute(value = "clientHeight")}))
    private Double height; //altura

    @Getter(onMethod = @__({@DynamoDbAttribute(value = "clientWeight")}))
    private Double weight; //peso

    @Getter(onMethod = @__({@DynamoDbAttribute(value = "clientSalary")}))
    private Double salary; //salario

    @Getter(onMethod = @__({@DynamoDbAttribute(value = "clientAddress")}))
    private String address; // endereço
}
