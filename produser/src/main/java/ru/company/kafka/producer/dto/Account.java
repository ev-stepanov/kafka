package ru.company.kafka.producer.dto;

import lombok.Builder;
import lombok.Data;
import ru.company.kafka.producer.enums.TypeBankAccount;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Long balance;
    private TypeBankAccount typeBankAccount;
}
