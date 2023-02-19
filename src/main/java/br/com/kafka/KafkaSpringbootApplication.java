package br.com.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(scanBasePackages = { "br.com.kafka" })
@EnableFeignClients(basePackages = { "br.com.kafka" })
@Slf4j
public class KafkaSpringbootApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		System.setProperty("logging.level.web", "DEBUG");
		SpringApplication.run(KafkaSpringbootApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(
			@Value("${spring.profiles.active}") String valueDoesExist,
			@Value("${redis.aws.server}") String valueDoesNotExist) {
		return args -> {
			log.info("message from application.properties " + valueDoesExist);
			log.info("missing message from application.properties " + valueDoesNotExist);
		};
	}
}
