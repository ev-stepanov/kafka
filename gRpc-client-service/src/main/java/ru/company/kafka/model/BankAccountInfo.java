package ru.company.kafka.model;

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
    private UUID uuid;
    private BankAccount bankAccount;
    private Address address;
}
