package br.com.kafka.application.config;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.Jedis;

//@Configuration
public class RedisAwsConfig {

    @Value("${redis.aws.server}")
    private String redisAwsServer;

    @Value("${redis.aws.port}")
    private Integer redisAwsPort;

    @Value("${redis.aws.timeout}")
    private Integer clientTimeoutInSecs;

    @Autowired
    AWSSecretsManager awsSecretsManager;

    @Bean
    @Profile("!local")
    public Jedis jedis() {

        Jedis jedis = new Jedis(redisAwsServer, redisAwsPort);

        String secretValue = awsSecretsManager.getSecretValue(new GetSecretValueRequest().withSecretId("redis-elasticache-secret")).getSecretString();
        JSONObject jsonObject = new JSONObject(secretValue);
        jedis.auth(jsonObject.getString("password"));

        return jedis;
    }

    @Bean
    @Profile("local")
    public Jedis jedisLocal() throws Exception {
        Jedis jedis = new Jedis("localhost", 9999, true);

        String secretValue = awsSecretsManager.getSecretValue(new GetSecretValueRequest().withSecretId("redis-elasticache-secret")).getSecretString();
        JSONObject jsonObject = new JSONObject(secretValue);
        jedis.auth(jsonObject.getString("password"));

        return jedis;
    }

}
