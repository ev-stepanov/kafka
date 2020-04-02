package ru.company.kafka.model.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private UUID uuid;
    private String city;
    private String street;
    private String state;
}
