package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private UserClientRepository clientRepository;

    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(clientId);
        return ResponseEntity.ok("Client deleted successfully");
    }
}