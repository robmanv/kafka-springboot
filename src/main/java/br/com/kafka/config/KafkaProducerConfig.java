package br.com.kafka.config;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String brokersUrl;

    @Value("${spring.kafka.schema.registry.url}")
    private String registry;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @org.springframework.context.annotation.Bean
    public KafkaProducer<String, GenericRecord> kafkaProducer() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokersUrl);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        producerProps.put("schema.registry.url", registry);

        return new KafkaProducer(producerProps);
    }
}

//        Properties properties = new Properties();
//
//        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        properties.put(ACKS_CONFIG, kafkaProperties.getAcksConfig());
//        properties.put(RETRIES_CONFIG, kafkaProperties.getRetriesConfig());
//        properties.put(KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
//        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());
//        properties.put(SCHEMA_REGISTRY_URL_CONFIG, kafkaProperties.getSchemaRegistryUrl());

//        return new KafkaProducer<String, Cliente>(properties);

