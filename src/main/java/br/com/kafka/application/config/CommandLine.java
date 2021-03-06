package br.com.kafka.application.config;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.entities.Post;
import br.com.kafka.core.ports.ListenerKafkaPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Configuration
public class CommandLine implements CommandLineRunner {

    @Autowired
    Cliente cliente;

    @Autowired
    ListenerKafkaPort listenerKafkaPort;

    @Autowired
    JSONPlaceHolderClient jsonPlaceHolderClient;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Robson");
        System.out.println(cliente.toString());
        System.out.println();

        ObjectMapper mapper = new ObjectMapper();

        List<Post> postagem = jsonPlaceHolderClient.getPosts();

        String postagemJson = mapper.writeValueAsString(postagem.get(0));

        System.out.println("RESPONSE-ENTITY        :" + ResponseEntity.ok().body(postagemJson));
        System.out.println("POSTS DA API - JSON    : " + postagemJson);
        System.out.println("POSTS DA API - TOSTRING: " + postagem.get(0).toString());

//        producerKafkaPort.send(cliente.toString());
    }
}
