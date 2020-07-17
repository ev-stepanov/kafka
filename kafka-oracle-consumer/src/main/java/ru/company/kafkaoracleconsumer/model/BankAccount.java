package ru.company.kafkaoracleconsumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    private UUID uuid;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    private Double balance;
    private TypeAccount typeAccount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public enum TypeAccount {
        DEBIT, CREDIT
    }
}
