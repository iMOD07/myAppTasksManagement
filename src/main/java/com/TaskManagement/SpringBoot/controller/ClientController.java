package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.model.UserClient;
import com.TaskManagement.SpringBoot.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private UserClientService clientService;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserClient currentClient)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        clientService.deleteClient(currentClient.getId());
        return ResponseEntity.ok("Your account has been deleted successfully");
    }




}
