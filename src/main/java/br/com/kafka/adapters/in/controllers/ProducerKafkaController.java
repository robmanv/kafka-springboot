package br.com.kafka.adapters.in.controllers;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.ConsumerKafkaPort;
import br.com.kafka.core.ports.ProducerKafkaPort;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.rmi.ServerException;

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
        cliente = new Cliente(1, "Robson Vellasques", 40, 1.81, 105.0, 1234.0, "Rua Vergueiro");
        producerKafkaPort.send(cliente);
        System.out.println("Registro produzido: " + cliente.toString());
    }

    @PostMapping(value = "/send")
    public ResponseEntity<Cliente> create(@RequestBody Cliente newCliente) {
        Cliente cliente = producerKafkaPort.send(newCliente);
        if (cliente == null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        }
    }

}
