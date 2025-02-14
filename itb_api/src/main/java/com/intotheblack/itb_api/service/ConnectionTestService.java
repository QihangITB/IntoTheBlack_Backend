package com.intotheblack.itb_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intotheblack.itb_api.repository.PlayerRepository;

@Service
public class ConnectionTestService {
    @Autowired
    private PlayerRepository playerRepository;

    // Método que verifica si la base de datos está conectada.
    public boolean isDatabaseConnected() {
        try {
            // Intentamos obtener al menos un jugador de la base de datos
            long count = playerRepository.count(); // Esto hace una consulta simple para contar los jugadores
            return count >= 0; // Si la consulta funciona, la base de datos está conectada
        } catch (Exception e) {
            return false; // Si ocurre algún error, no se puede conectar a la base de datos
        }
    }
}
