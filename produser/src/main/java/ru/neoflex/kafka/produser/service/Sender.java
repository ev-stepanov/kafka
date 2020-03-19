package ru.neoflex.kafka.produser.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.neoflex.kafka.produser.dto.Employee;

@Service
@Log4j2
public class Sender {
    @Value("${spring.kafka.topic-name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate;

    public void sendMessage(Employee employee) {

        ListenableFuture<SendResult<String, Employee>> future = kafkaTemplate.send(topicName, employee);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Employee>>() {
            @Override
            public void onSuccess(SendResult<String, Employee> result) {
                log.info("Sent message=[" + employee + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + employee + "] due to : " + ex.getMessage());
            }
        });
    }
}
