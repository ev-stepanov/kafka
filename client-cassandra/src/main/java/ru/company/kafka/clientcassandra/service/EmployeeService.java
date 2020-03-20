package ru.company.kafka.clientcassandra.service;

import ru.company.kafka.clientcassandra.dto.EmployeeDto;
import ru.company.kafka.clientcassandra.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee findById(Long id);
    List<Employee> findAll();
    Employee save(EmployeeDto employeeDto);
}
