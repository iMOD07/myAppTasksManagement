package com.TaskManagement.SpringBoot.service;

import com.TaskManagement.SpringBoot.model.UserClient;
import com.TaskManagement.SpringBoot.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserClientService {

    @Autowired
    private UserClientRepository clientRepository;

    public UserClient registerClient(String fullName,
                                     String email,
                                     String passwordHash,
                                     String mobileNumber,
                                     String companyName,
                                     String address) {
        if (clientRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (clientRepository.findByMobileNumber(mobileNumber).isPresent()) {
            throw new RuntimeException("Mobile Number already exists");
        }

        UserClient client = new UserClient();
        client.setFullName(fullName);
        client.setEmail(email);
        client.setPasswordHash(passwordHash);
        client.setMobileNumber(mobileNumber);
        client.setCompanyName(companyName);
        client.setAddress(address);
        return clientRepository.save(client);
    }

    // الدالة الجديدة للبحث عن العميل بواسطة البريد الإلكتروني
    public Optional<UserClient> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
