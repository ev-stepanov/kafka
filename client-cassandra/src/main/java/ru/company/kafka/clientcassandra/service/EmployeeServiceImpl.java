package ru.company.kafka.clientcassandra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.company.kafka.clientcassandra.dto.EmployeeDto;
import ru.company.kafka.clientcassandra.model.Employee;
import ru.company.kafka.clientcassandra.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .birthday(employeeDto.getBirthday())
                .subordinates(employeeDto.getSubordinates())
                .build();
        return employeeRepository.save(employee);
    }
}
