package br.com.kafka.application.config;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ListenerKafkaPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class CommandLine implements CommandLineRunner {

    @Autowired
    Cliente cliente;

    @Autowired
    ListenerKafkaPort listenerKafkaPort;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Robson");
        System.out.println(cliente.toString());
//        producerKafkaPort.send(cliente.toString());
    }
}
