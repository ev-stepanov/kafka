package ru.company.kafka.clientcassandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.kafka.clientcassandra.dto.EmployeeDto;
import ru.company.kafka.clientcassandra.model.Employee;
import ru.company.kafka.clientcassandra.service.EmployeeService;

import java.util.List;

@RestController("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping("/employees}")
    public List<Employee> getProducts() {
        return employeeService.findAll();
    }

    @PostMapping("/employee")
    public Employee save(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }
}
