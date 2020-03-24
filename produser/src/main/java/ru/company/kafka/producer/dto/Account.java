package ru.company.kafka.producer.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Account {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Long balance;
}
