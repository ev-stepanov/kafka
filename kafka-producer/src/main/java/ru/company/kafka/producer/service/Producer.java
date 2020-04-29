package ru.company.kafka.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.company.kafka.model.producer.BankAccountDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {
    private KafkaTemplate<String, BankAccountDto> kafkaTemplate;

    @Value("${spring.kafka.topic-name}")
    private String topicName;

    void sendMessage(BankAccountDto bankAccountDto) {
        ListenableFuture<SendResult<String, BankAccountDto>> future =
                kafkaTemplate.send(
                        topicName,
                        bankAccountDto.getUuid().toString(),
                        bankAccountDto
                );

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, BankAccountDto> result) {
                log.info("Topic: " + result.getRecordMetadata().topic() +
                        "   Offset: " + result.getRecordMetadata().offset() +
                        "   Partition: " + result.getRecordMetadata().partition() +
                        "   Sent message=[" + bankAccountDto + "]");
            }

            @Override
            public void onFailure(@SuppressWarnings("NullableProblems") Throwable ex) {
                log.error("Unable to send message=[" + bankAccountDto + "] due to : " + ex.getMessage());
            }
        });
    }
}
