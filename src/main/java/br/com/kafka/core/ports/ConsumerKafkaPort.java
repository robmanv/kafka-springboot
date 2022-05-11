package br.com.kafka.core.ports;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerKafkaPort {
    public void consume(ConsumerRecord<String, String> payload);
}
