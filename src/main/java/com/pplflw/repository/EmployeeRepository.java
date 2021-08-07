package com.pplflw.repository;

import com.pplflw.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long id);
}
