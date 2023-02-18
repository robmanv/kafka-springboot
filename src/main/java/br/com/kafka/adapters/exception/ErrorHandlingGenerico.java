package br.com.kafka.adapters.exception;

import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.FixedBackOff;

public class ErrorHandlingGenerico extends ErrorHandlingDeserializer<Void> {
    @Autowired
    ConcurrentKafkaListenerContainerFactory factory;

//    @Override
//    public Void deserialize(String topic, Headers headers, byte[] data) {
//        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L)));
//
//    }

}
