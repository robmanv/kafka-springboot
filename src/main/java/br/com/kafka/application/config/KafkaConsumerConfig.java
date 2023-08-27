package br.com.kafka.application.config;

import br.com.kafka.core.entities.Cliente;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    // kafka broker list.
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    // schema registry url.
    @Value("${spring.kafka.schema.registry.url}")
    private String schemaRegistryAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Bean
    public KafkaConsumer<Long, Cliente> consumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://broker:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, keyDeserializer);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, valueDeserializer);
        props.put("schema.registry.url", schemaRegistryAddress);
//        props.put("schema.registry.url", "http://schema-registry:8081");
        props.put("specific.avro.reader", true);

        return new KafkaConsumer<Long, Cliente>(props);
    }
}