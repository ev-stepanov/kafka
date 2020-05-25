package ru.company.kafka.rsocketserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String city;
    private String street;
    private String state;
}
