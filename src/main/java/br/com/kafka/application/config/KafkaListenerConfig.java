package br.com.kafka.application.config;

import br.com.kafka.core.entities.Cliente;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaListenerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.schema.registry.url}")
    private String schemaRegistryAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @org.springframework.context.annotation.Bean
    public ConsumerFactory<Long, Cliente> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://broker:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put("schema.registry.url", schemaRegistryAddress);
//        props.put("schema.registry.url", "http://schema-registry:8081");
        props.put("specific.avro.reader", true);

        ErrorHandlingDeserializer errorHandlingDeserializer = new ErrorHandlingDeserializer(new KafkaAvroDeserializer());

        return new DefaultKafkaConsumerFactory(props, new LongDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Cliente>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<Long, Cliente> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRetryTemplate(retryTemplate());
        factory.setRecoveryCallback((context -> {
            if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException){
                //here you can do your recovery mechanism where you can put back on to the topic using a Kafka producer
                factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L)));
            } else{
                // here you can log things and throw some custom exception that Error handler will take care of ..
                throw new RuntimeException(context.getLastThrowable().getMessage());
            }
            return null;
        }));
        factory.setErrorHandler(((exception, data) -> {
           /* here you can do you custom handling, I am just logging it same as default Error handler does
          If you just want to log. you need not configure the error handler here. The default handler does it for you.
          Generally, you will persist the failed records to DB for tracking the failed records.  */
            log.error("Error in process with Exception {} and the record is {}", exception, data);
        }));


        return factory;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        /* here retry policy is used to set the number of attempts to retry and what exceptions you wanted to try and what you don't want to retry.*/
        retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
        return retryTemplate;
    }
    private SimpleRetryPolicy getSimpleRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        return new SimpleRetryPolicy(3,exceptionMap,true);
    }

}