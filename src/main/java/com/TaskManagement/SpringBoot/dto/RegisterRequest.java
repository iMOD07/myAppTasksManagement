package com.TaskManagement.SpringBoot.dto;

import com.TaskManagement.SpringBoot.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}
