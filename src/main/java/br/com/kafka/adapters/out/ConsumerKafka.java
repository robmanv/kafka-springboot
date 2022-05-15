package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ConsumerKafkaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerKafka implements ConsumerKafkaPort {

    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Autowired
    Cliente cliente;

    @Autowired
    ModelMapper modelMapper;

    public Cliente consume() {
        try {
            kafkaConsumer.subscribe(Collections.singletonList(topicName));

            ConsumerRecords<Long, Cliente> records = kafkaConsumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<Long, Cliente> record : records) {
                System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
                cliente = modelMapper.map(record.value(), Cliente.class);

                return cliente;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
