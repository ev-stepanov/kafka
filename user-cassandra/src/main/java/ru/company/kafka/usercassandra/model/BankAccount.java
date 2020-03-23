package ru.company.kafka.usercassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @PrimaryKey
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Long balance;
}
