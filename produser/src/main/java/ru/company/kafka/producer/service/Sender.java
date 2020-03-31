package ru.company.kafka.producer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.company.kafka.model.Account;

@Service
@Log4j2
public class Sender {
    @Value("${spring.kafka.topic-name}")
    private String topicName;

    private KafkaTemplate<String, Account> kafkaTemplate;

    @Autowired
    public Sender(KafkaTemplate<String, Account> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Account account) {

        ListenableFuture<SendResult<String, Account>> future = kafkaTemplate.send(topicName,account.getUuid().toString(), account);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Account>>() {
            @Override
            public void onSuccess(SendResult<String, Account> result) {
                log.info("Sent message=[" + account + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + account + "] due to : " + ex.getMessage());
            }
        });
    }
}
