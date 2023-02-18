package br.com.kafka.application.config;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.adapters.exception.CustomErrorDecoder;
import br.com.kafka.adapters.out.ListenerKafka;
import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.entities.Post;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.LoggingErrorHandler;

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

    @org.springframework.context.annotation.Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @org.springframework.context.annotation.Bean
    public AWSSecretsManager awsSecretsManager() {
        Region region = Region.getRegion(Regions.SA_EAST_1);
        return AWSSecretsManagerClient.builder()
                .withRegion(String.valueOf(region))
                .withCredentials(awsCredentialsProvider())
                .build();
    }

    @org.springframework.context.annotation.Bean
    public LoggingErrorHandler errorHandler() {
        return new LoggingErrorHandler();
    }

}