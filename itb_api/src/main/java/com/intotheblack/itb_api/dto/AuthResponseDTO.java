package com.intotheblack.itb_api.dto;

import com.intotheblack.itb_api.model.User;

public class AuthResponseDTO {
    
    String token;
    User user;

    // Constructor
    public AuthResponseDTO(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
