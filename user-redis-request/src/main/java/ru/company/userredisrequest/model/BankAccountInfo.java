package ru.company.userredisrequest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("bank_account_info")
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
