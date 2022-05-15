package br.com.kafka.config;

import br.com.kafka.core.entities.Cliente;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaListenerConfig {

    // kafka broker list.
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    // schema registry url.
    @Value("${spring.kafka.schema.registry.url}")
    private String schemaRegistryAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @org.springframework.context.annotation.Bean
    public ConsumerFactory<Long, Cliente> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put("schema.registry.url", schemaRegistryAddress);
        props.put("specific.avro.reader", true);

        return new DefaultKafkaConsumerFactory(props, new LongDeserializer(), new KafkaAvroDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Cliente>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<Long, Cliente> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}