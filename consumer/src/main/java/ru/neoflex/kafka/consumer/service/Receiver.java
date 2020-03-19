package ru.neoflex.kafka.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.neoflex.kafka.consumer.dto.Employee;

@Service
@Log4j2
public class Receiver {

    @KafkaListener(topics = "topic")
    public void listen(Employee employee) {
        log.info("Received message: " + employee);
    }
}
