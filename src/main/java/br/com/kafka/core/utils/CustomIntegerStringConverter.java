package br.com.kafka.core.utils;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CustomIntegerStringConverter implements AttributeConverter<Integer> {

    @Override
    public AttributeValue transformFrom(Integer input) {
        return AttributeValue.builder().s(String.valueOf(input)).build();
    }

    @Override
    public Integer transformTo(AttributeValue input) {
        return Integer.valueOf(input.n());
    }

    @Override
    public EnhancedType<Integer> type() {
        return EnhancedType.of(Integer.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
