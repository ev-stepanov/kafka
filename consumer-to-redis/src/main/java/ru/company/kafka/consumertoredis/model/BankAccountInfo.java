package ru.company.kafka.consumertoredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@RedisHash("bank_account_info")
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountInfo {
    @Id
    private String uuid;
    private Address address;
    private BankAccount bankAccount;

    public BankAccountInfo(BankAccount bankAccount, Address address) {
        uuid = bankAccount.getUuid().toString();
        this.address = address;
        this.bankAccount = bankAccount;
    }
}
