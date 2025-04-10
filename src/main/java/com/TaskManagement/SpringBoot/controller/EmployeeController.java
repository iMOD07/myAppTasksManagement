package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.repository.UserEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private UserEmployeeRepository employeeRepository;

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteUserEmployee(@PathVariable Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            return ResponseEntity.notFound().build();
        }
        employeeRepository.deleteById(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}