package ru.company.kafka.rsocketserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountInfo {
    private String uuid;
    private BankAccount bankAccount;
    private Address address;
}
