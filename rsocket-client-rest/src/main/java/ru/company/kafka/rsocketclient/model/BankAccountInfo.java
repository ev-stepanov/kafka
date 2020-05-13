package ru.company.kafka.rsocketclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountInfo {
    private String uuid;

    private BankAccount bankAccount;

    private Address address;

}
