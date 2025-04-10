package com.TaskManagement.SpringBoot.service;

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
        return employeeRepository.save(employee);
    }

    // الدالة الجديدة للبحث عن الموظف بواسطة البريد الإلكتروني
    public Optional<UserEmployee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
