package ru.company.kafkaoracleconsumer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private String city;
    private String street;
    private String state;
}

