package br.com.kafka.application.config;

import br.com.kafka.adapters.out.ListenerKafka;
import br.com.kafka.core.entities.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bean {

//    private Cliente cliente = new Cliente(1, "Robson Vellasques", 40, 1.81, 105.0, 10000.0, "Rua Vergueiro");

    @org.springframework.context.annotation.Bean
    public Cliente cliente() {
        return new Cliente();
    }

    @org.springframework.context.annotation.Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}