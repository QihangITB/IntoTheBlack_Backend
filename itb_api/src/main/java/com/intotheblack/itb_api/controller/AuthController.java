package com.intotheblack.itb_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import com.intotheblack.itb_api.dto.AuthResponseDTO;
import com.intotheblack.itb_api.dto.UserLoginDTO;
import com.intotheblack.itb_api.dto.UserRegisterDTO;
import com.intotheblack.itb_api.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para registrar y loguear usuarios")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
