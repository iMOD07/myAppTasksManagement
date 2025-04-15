package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.model.Role;
import com.TaskManagement.SpringBoot.model.UserEmployee;
import com.TaskManagement.SpringBoot.service.UserEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private UserEmployeeService employeeService; // تعريف المتغير هنا

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteUserEmployee(@PathVariable Long employeeId) {
        if (!employeeService.existsById(employeeId)) {
            return ResponseEntity.ok("User not Found");
        }
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole/{employeeId}")
    public ResponseEntity<UserEmployee> updateEmployeeRole(@PathVariable Long employeeId,
                                                           @RequestParam String role) {
        Role newRole;
        try {
            newRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        UserEmployee updatedEmployee = employeeService.updateEmployeeRole(employeeId, newRole);
        return ResponseEntity.ok(updatedEmployee);
    }
}
