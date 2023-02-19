package br.com.kafka.steps;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.config.Config;
import br.com.kafka.core.entities.Post;
import br.com.kafka.helpers.ApiMocks;
import br.com.kafka.helpers.HelpersObjects;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class CenarioUm {
    @BeforeEach
    void setUp() throws IOException {
        ApiMocks.setupMockPostResponse(wireMockServer);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    JSONPlaceHolderClient jsonPlaceHolderClient;

    @Autowired
    ObjectMapper mapper;

    String postagemJson;

    @Dado("um cenario de chamada api JSONPlaceHolderClient")
    public void umCenarioDeChamadaApiJSONPlaceHolderClient() throws IOException {
        wireMockServer.start();
        ApiMocks.setupMockPostResponse(wireMockServer);
        Integer beanCount = applicationContext.getBeanDefinitionCount();
        String[] beanList = applicationContext.getBeanDefinitionNames();
    }

    @Quando("obter uma lista de mensagens")
    public void obterUmaListaDeMensagens() throws IOException {
        ResponseEntity<List<Post>> postagem = jsonPlaceHolderClient.getPosts();

        this.postagemJson = mapper.writeValueAsString(postagem.getBody().get(0));
    }

    @Entao("finalizamos a chamada da api JSONPlaceHolderClient")
    public void finalizamosAChamadaDaApiJSONPlaceHolderClient() throws JsonProcessingException {

        Assertions.assertEquals(this.postagemJson, mapper.writeValueAsString(HelpersObjects.getUsuarioUm()));
        wireMockServer.stop();
    }

}
