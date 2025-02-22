package com.intotheblack.itb_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intotheblack.itb_api.repository.UserRepository;

@Service
public class ConnectionService {
    @Autowired
    private UserRepository userRepository;

    public boolean isDatabaseConnected() {
        try {
            // Intentamos obtener al menos un usuario de la base de datos
            long count = userRepository.count();
            return count >= 0;
        } catch (Exception e) {
            return false;
        }
    }
}
