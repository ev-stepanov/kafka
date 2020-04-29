package ru.company.kafka.kafkaconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("bank-account-info")
public class BankAccountInfo implements Serializable {
    @Id
    private String uuid;
    private BankAccount bankAccount;
    private Address address;
}
