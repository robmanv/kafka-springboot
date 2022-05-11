package br.com.kafka.config;

import br.com.kafka.adapters.out.ProducerKafka;
import br.com.kafka.core.entities.Cliente;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class Bean {

    private Cliente cliente = new Cliente(1, "Robson Vellasques", 40, 1.81, 105.0);

    @org.springframework.context.annotation.Bean
    public Cliente cliente() {
        return cliente;
    }

}
