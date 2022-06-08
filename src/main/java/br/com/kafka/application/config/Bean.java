package br.com.kafka.application.config;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.adapters.exception.CustomErrorDecoder;
import br.com.kafka.adapters.out.ListenerKafka;
import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.entities.Post;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @org.springframework.context.annotation.Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @org.springframework.context.annotation.Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @org.springframework.context.annotation.Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("user", "user");
            requestTemplate.header("password", "password");
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }

}