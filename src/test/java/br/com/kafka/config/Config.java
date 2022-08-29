package br.com.kafka.config;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.core.entities.Post;
import br.com.kafka.helpers.ApiMocks;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.google.gson.Gson;
import io.cucumber.spring.CucumberContextConfiguration;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@CucumberContextConfiguration
@ContextConfiguration( classes = Config.class )
public class Config {
    @RegisterExtension
    static WireMockExtension INVENTORY_SERVICE = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8089))
            .build();
    @Bean
    public WireMockServer wireMockServer() {
        return new WireMockServer(wireMockConfig().port(8089));
    }

    @Bean
    public JSONPlaceHolderClient jsonPlaceHolderClient() {
        return new JSONPlaceHolderClient() {
            @Override
            public List<Post> getPosts() {
                ObjectMapper objectMapper = new ObjectMapper();
                Object objs = null;
                try {
                    objs = objectMapper.readValue(new File("src/test/resources/payload/get-json-placeholder-client-response.json"), Object.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<Post> listaPost = new ArrayList<>();

                for (Object obj : (List) objs) {
                    listaPost.add(objectMapper.convertValue(obj, Post.class));
                }

                return listaPost;
            }

            @Override
            public Post getPostById(Long postId) {
                return null;
            }
        };
    }



}
