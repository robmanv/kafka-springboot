package br.com.kafka.core.ports;

import br.com.kafka.core.entities.Cliente;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ListenerKafkaPort {
    public void consume(ConsumerRecord<String, Cliente> payload);
}
