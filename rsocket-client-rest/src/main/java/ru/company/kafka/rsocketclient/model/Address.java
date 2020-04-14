package ru.company.kafka.rsocketclient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Address {
    private String city;
    private String street;
    private String state;
}
