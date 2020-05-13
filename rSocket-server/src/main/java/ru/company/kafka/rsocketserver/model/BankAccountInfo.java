package ru.company.kafka.rsocketserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("bank-account-info")
public class BankAccountInfo {
    @Id
    private String uuid;
    private BankAccount bankAccount;
    private Address address;
}
