package ru.company.kafka.kafkaconsumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import ru.company.kafka.model.enums.TypeAccount;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("bank_account_info")
public class BankAccountInfo {
    @PrimaryKey
    private UUID uuid;

    private String firstName;
    private String lastName;
    private Double balance;
    private TypeAccount typeAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    private String city;
    private String street;
    private String state;
}
