package br.com.kafka.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Value("${redis.server}")
    private String redisServer;

    @Bean
    public Jedis redisFactory() {
        return new Jedis(redisServer);
    }
}
