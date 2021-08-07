package com.pplflw.controller;

import com.pplflw.dto.EmployeeDTO;
import com.pplflw.dto.StateDTO;
import com.pplflw.model.Employee;
import com.pplflw.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.pplflw.State.ADDED;

@RestController()
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an employee by its id")
    public Employee getEmployee(@PathVariable long id) {
        return employeeRepository.findById(id);
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Operation(summary = "Add an employee")
    @PostMapping
    public Employee addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        employee.setState(ADDED);
        return employeeRepository.save(employee);
    }

    @Operation(summary = "Update an employee state")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateState(@PathVariable Long id, @RequestBody StateDTO stateDTO) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setState(stateDTO.getState());
            employeeRepository.save(employee);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
