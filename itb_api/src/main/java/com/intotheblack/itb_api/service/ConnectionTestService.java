package com.intotheblack.itb_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConnectionTestService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método que verifica si la base de datos está conectada
    public boolean isDatabaseConnected() {
        try {
            String sql = "SELECT COUNT(*) FROM player";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }
}
