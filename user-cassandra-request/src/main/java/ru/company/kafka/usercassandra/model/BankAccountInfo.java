package ru.company.kafka.usercassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("bank_account_info")
public class BankAccountInfo {
    @PrimaryKey
    private UUID uuid;

    @Column("bank_account")
    private BankAccount bankAccount;

    @Column("address")
    private Address address;

}
