package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ProducerKafkaPort;
import br.com.kafka.core.services.ProducerKafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerKafka implements ProducerKafkaPort {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    ProducerKafkaService producerKafkaService;

    public void send(String message){
        log.info("Payload enviado: {}", message);
        kafkaTemplate.send(topicName, message);
    }

    public Cliente send(Cliente cliente) {
        log.info("Producer kafka rota send_body: {}", cliente.toString());

        try {
            GenericRecord genericRecord = producerKafkaService.gerarSchema(cliente);
            ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>(topicName, genericRecord);
            kafkaProducer.send(producerRecord);

            return cliente;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }

}
