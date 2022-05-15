package br.com.kafka.core.ports;

import br.com.kafka.core.entities.Cliente;

public interface ProducerKafkaPort {
    public void send(String message);
    public Cliente send(Cliente cliente);
}
