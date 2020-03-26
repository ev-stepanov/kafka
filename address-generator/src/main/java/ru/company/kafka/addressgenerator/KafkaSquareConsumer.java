package ru.company.kafka.addressgenerator;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.company.kafka.addressgenerator.dto.AccountDto;

@Component
public class KafkaSquareConsumer {

    @KafkaListener(topics = "${kafka.topic.square-output}", groupId = "random-consumer")
    public void consume(AccountDto accountDto)  {
        System.out.println("Consumed ::" + accountDto);
    }

}