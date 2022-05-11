package br.com.kafka.core.ports;

public interface ProducerKafkaPort {
    public void send(String message);
}
