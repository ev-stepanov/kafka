package ru.company.kafka.kafkaconsumer.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("address")
public class Address implements Serializable {
    private String city;
    private String street;
    private String state;
}
