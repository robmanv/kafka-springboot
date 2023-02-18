package br.com.kafka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

import javax.batch.runtime.JobExecution;

@EnableKafka
@SpringBootApplication(scanBasePackages = { "br.com.kafka" })
@EnableFeignClients(basePackages = { "br.com.kafka" })
public class KafkaSpringbootApplication {

	public static void main(String[] args) {

//		SpringApplication.exit(SpringApplication.run(KafkaSpringbootApplication.class, args));
		SpringApplication.run(KafkaSpringbootApplication.class, args);
	}

}
