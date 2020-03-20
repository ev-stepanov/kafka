package ru.company.kafka.clientcassandra.dto;

import lombok.Builder;
import lombok.Data;
import ru.company.kafka.clientcassandra.model.Employee;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private List<Employee> subordinates;
}
