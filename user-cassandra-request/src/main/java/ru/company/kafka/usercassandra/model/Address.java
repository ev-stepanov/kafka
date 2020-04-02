package ru.company.kafka.usercassandra.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType("address")
class Address {
    private String city;
    private String street;
    private String state;
}
