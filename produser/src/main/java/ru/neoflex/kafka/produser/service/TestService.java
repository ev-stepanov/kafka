package ru.neoflex.kafka.produser.service;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@Service
@Log4j2
public class TestService {
    @Value("${spring.kafka.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Scheduled(cron = "*/5 * * * * *")
    public void sendMessage() {
        String timeNow = LocalDateTime.now().toLocalTime().toString();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, timeNow);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + timeNow + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + timeNow + "] due to : " + ex.getMessage());
            }
        });
    }
}
