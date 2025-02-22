package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.dto.AuthResponseDTO;
import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.RegisterRequestDTO;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setUsername("user1");
        loginDTO.setPassword("password123");

        User user = new User();
        user.setUsername("user1");

        AuthResponseDTO authResponseDTO = new AuthResponseDTO("token", user);

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(authResponseDTO);

        ResponseEntity<Object> response = authController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponseDTO, response.getBody());
    }

    @Test
    void testLoginFailure() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setUsername("user1");
        loginDTO.setPassword("wrongpassword");

        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        ResponseEntity<Object> response = authController.login(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequestDTO registerDTO = new RegisterRequestDTO();
        registerDTO.setUsername("user1");
        registerDTO.setPassword("password123");
        registerDTO.setEmail("user1@example.com");

        User user = new User();
        user.setUsername("user1");

        AuthResponseDTO authResponseDTO = new AuthResponseDTO("token", user);

        when(authService.register(any(RegisterRequestDTO.class))).thenReturn(authResponseDTO);

        ResponseEntity<Object> response = authController.register(registerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(authResponseDTO, response.getBody());
    }

    @Test
    void testRegisterFailure() {
        RegisterRequestDTO registerDTO = new RegisterRequestDTO();
        registerDTO.setUsername("user1");
        registerDTO.setPassword("password123");
        registerDTO.setEmail("user1@example.com");

        when(authService.register(any(RegisterRequestDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken"));

        ResponseEntity<Object> response = authController.register(registerDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username is already taken", response.getBody());
    }
}