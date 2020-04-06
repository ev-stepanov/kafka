package ru.company.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import ru.company.kafka.model.enums.TypeAccount;

import java.time.LocalDate;

@Data
@Builder
@UserDefinedType("bank_account")
public class BankAccount {
    private String firstName;
    private String lastName;
    private Double balance;
    @Indexed
    private TypeAccount typeAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
}
