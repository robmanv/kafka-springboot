package br.com.kafka.core.ports;

import br.com.kafka.core.entities.Cliente;

public interface ConsumerKafkaPort {
    public Cliente consume();
}
