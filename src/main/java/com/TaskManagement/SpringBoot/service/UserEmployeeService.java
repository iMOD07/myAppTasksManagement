package com.TaskManagement.SpringBoot.service;

import com.TaskManagement.SpringBoot.model.Role;
import com.TaskManagement.SpringBoot.model.UserEmployee;
import com.TaskManagement.SpringBoot.repository.UserEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserEmployeeService {

    @Autowired
    private UserEmployeeRepository employeeRepository;

    public UserEmployee registerEmployee(String fullName,
                                         String email,
                                         String passwordHash,
                                         String mobileNumber,
                                         String department,
                                         String jobTitle) {
        if (employeeRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (employeeRepository.findByMobileNumber(mobileNumber).isPresent()) {
            throw new RuntimeException("Mobile Number already exists");
        }

        UserEmployee employee = new UserEmployee();
        employee.setFullName(fullName);
        employee.setEmail(email);
        employee.setPasswordHash(passwordHash);
        employee.setMobileNumber(mobileNumber);
        employee.setDepartment(department);
        employee.setJobTitle(jobTitle);
        employee.setRole(Role.EMPLOYEE); // عند تسجيل الموظف، يتم تعيين دوره كـ EMPLOYEE
        return employeeRepository.save(employee);
    }

    // دالة البحث عن الموظف بواسطة البريد الإلكتروني
    public Optional<UserEmployee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }


    public void deleteEmployee(Long employeeId) {
        // check the employees first .
        if(employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }


    public UserEmployee updateEmployeeRole(Long employeeId, Role newRole) {
        Optional<UserEmployee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new RuntimeException("Employee not found");
        }
        UserEmployee employee = optionalEmployee.get();
        employee.setRole(newRole);
        return employeeRepository.save(employee);
    }
}
