package br.com.kafka.core.ports;

import br.com.kafka.core.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

public interface AwsSqsPort {
    public String getQueueUrl();

    public void sendMessage(String queueUrl, String msgBody);
    public void sendMessageBatch(String queueUrl, List<String> msgBody);
    public List<String> receiveMessage(String queueUrl);
    public void deleteMessage(String queueUrl, List<String> messages);
}
