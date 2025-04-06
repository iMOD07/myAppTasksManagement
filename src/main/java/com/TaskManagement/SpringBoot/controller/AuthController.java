package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.model.User;
import com.TaskManagement.SpringBoot.model.Role;
import com.TaskManagement.SpringBoot.dto.LoginRequest;
import com.TaskManagement.SpringBoot.dto.RegisterRequest;
import com.TaskManagement.SpringBoot.dto.AuthResponse;
import com.TaskManagement.SpringBoot.service.UserService;
import com.TaskManagement.SpringBoot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getFullName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getRole());
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if(userOptional.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOptional.get().getPasswordHash())) {
            return ResponseEntity.status(401).body(new AuthResponse(null, null));
        }
        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
    }
}
