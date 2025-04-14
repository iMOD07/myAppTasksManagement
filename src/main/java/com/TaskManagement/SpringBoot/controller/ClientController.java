package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    // تعريف وحقن service الخاص بالعملاء
    @Autowired
    private UserClientService clientService;


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteUserClient(@PathVariable Long clientId) {
        clientService.deleteUserClient(clientId);
        return ResponseEntity.ok("clientId delete successfully");
    }



}
