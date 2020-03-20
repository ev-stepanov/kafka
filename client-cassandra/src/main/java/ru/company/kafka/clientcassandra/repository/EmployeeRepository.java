package ru.company.kafka.clientcassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.clientcassandra.model.Employee;

public interface EmployeeRepository extends CassandraRepository<Employee, Long> {
}
