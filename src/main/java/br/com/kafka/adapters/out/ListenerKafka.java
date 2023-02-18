package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ListenerKafkaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerKafka implements ListenerKafkaPort {

    @Value("${topic.name.consumer}")
    private String topicName;

    @Autowired
    ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cliente cliente;

    @Autowired
    private Jedis jedisFactory;

    @KafkaListener(topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
//            properties = {"schema.registry.url=localhost:8081",
//                        "value.deserializer=io.confluent.kafka.serializers.KafkaAvroDeserializer",
//                        "key.deserializer=org.apache.kafka.common.serialization.LongDeserializer"})
    public void consume(ConsumerRecord<String, Cliente> payload) {
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        cliente = modelMapper.map(payload.value(), Cliente.class);

        jedisFactory.set("chave1", cliente.toString());
        String value = jedisFactory.get("chave1");

        log.info("REDIS (CHAVE1) - GET --> {}", value);

        log.info("CONSUMER: {}", cliente);
    }
}
