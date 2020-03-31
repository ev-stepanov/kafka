package ru.company.kafka.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.company.kafka.model.producer.BankAccount;

@Service
@Slf4j
public class Producer {
    @Value("${spring.kafka.topic-name}")
    private String topicName;

    private KafkaTemplate<String, BankAccount> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, BankAccount> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(BankAccount bankAccount) {
        ListenableFuture<SendResult<String, BankAccount>> future =
                kafkaTemplate.send(topicName, bankAccount.getUuid().toString(), bankAccount);

        future.addCallback(new ListenableFutureCallback<SendResult<String, BankAccount>>() {
            @Override
            public void onSuccess(SendResult<String, BankAccount> result) {
                log.info("Topic: " + result.getRecordMetadata().topic() +
                        "   Offset: " + result.getRecordMetadata().offset() +
                        "   Partition: " + result.getRecordMetadata().partition() +
                        "   Sent message=[" + bankAccount + "]");
            }

            @Override
            public void onFailure(@SuppressWarnings("NullableProblems") Throwable ex) {
                log.error("Unable to send message=[" + bankAccount + "] due to : " + ex.getMessage());
            }
        });
    }
}
