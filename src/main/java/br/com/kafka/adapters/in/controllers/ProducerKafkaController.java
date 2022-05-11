package br.com.kafka.adapters.in.controllers;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ConsumerKafkaPort;
import br.com.kafka.core.ports.ProducerKafkaPort;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kafka")
public class ProducerKafkaController {
    @Autowired
    ProducerKafkaPort producerKafkaPort;

    @Autowired
    ConsumerKafkaPort consumerKafkaPort;

    @Autowired
    Cliente cliente;

    @GetMapping(value = "/send")
    public void send(){
        producerKafkaPort.send(cliente.toString());
        System.out.println("Registro produzido: " + cliente.toString());
    }

}
