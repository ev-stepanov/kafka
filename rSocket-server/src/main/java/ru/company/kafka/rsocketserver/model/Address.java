package ru.company.kafka.rsocketserver.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("address")
class Address {
    private String city;
    private String street;
    private String state;
}
