package ru.company.kafka.kafkaconsumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import ru.company.kafka.model.enums.TypeAccount;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@RedisHash("bank-account")
public class BankAccount implements Serializable {
    private String firstName;
    private String lastName;
    private Double balance;
    private TypeAccount typeAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
}
