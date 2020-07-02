package ru.company.kafka.consumertoredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("bank_account_info")
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountInfo {
    public static final String BANK_ACCOUNT_NAME_KEY = "bank-account-info:";
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
