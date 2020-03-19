package ru.neoflex.kafka.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TestService {

    @KafkaListener(topics = "topic")
    public void listen(String message) {
        log.info("Received message in group foo: " + message);
    }
}
