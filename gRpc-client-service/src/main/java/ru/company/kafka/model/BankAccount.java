package ru.company.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;
import lombok.Data;
import ru.company.kafka.model.enums.TypeAccount;

import java.time.LocalDate;

@Data
@Builder
public class BankAccount {
    private String firstName;
    private String lastName;
    private Double balance;
    private TypeAccount typeAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
}
