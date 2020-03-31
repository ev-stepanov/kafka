package ru.company.kafka.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Long balance;
}
