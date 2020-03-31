package ru.company.kafka.addressgenerator;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
//import ru.company.kafka.addressgenerator.dto.AddressDto;
import ru.company.kafka.model.Account;

@Component
public class KafkaSquareConsumer {

//    @KafkaListener(topics = "${kafka.topic.output}", groupId = "random-consumer")
    public void consume(Account accountDto)  {
        System.out.println("Consumed ::" + accountDto);
    }

}