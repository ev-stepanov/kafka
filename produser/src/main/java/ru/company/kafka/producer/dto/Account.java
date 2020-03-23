package ru.company.kafka.producer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    private Long balance;
}
