package br.com.kafka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(scanBasePackages = { "br.com.kafka" })
public class KafkaSpringbootApplication {

	public static void main(String[] args) {

		SpringApplication.run(KafkaSpringbootApplication.class, args);
	}

}
