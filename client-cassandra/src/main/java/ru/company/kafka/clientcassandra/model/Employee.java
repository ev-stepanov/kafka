package ru.company.kafka.clientcassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @PrimaryKey
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private List<Employee> subordinates;
}
