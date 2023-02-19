package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.ports.AwsSqsPort;
import br.com.kafka.core.ports.ConsumerKafkaPort;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
//@Service
@RequiredArgsConstructor
public class AwsSqsAdapter implements AwsSqsPort {

    //TODO declarar em application.yml
    @Value("${sqs.queue.name}")
    private String sqsQueueName;

    @Autowired
    AmazonSQS amazonSQS;

    @Override
    public String getQueueUrl() {
        return amazonSQS.getQueueUrl(sqsQueueName).getQueueUrl();
    }

    @Override
    public void sendMessage(String queueUrl, String msgBody) {
        amazonSQS.sendMessage(
                new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msgBody));

    }

    @Override
    public void sendMessageBatch(String queueUrl, List<String> msgBody) {
//        amazonSQS.sendMessage(
//                new SendMessageBatchRequest()
//                        .withQueueUrl(sqsQueueName)
//                        .withEntries(msg));
    }

    @Override
    public List<String> receiveMessage(String queueUrl) {
        return null;
    }

    @Override
    public void deleteMessage(String queueUrl, List<String> messages) {

    }
}
