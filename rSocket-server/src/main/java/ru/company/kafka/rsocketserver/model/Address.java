package ru.company.kafka.rsocketserver.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType("address")
public class Address {
    private String city;
    private String street;
    private String state;
}
