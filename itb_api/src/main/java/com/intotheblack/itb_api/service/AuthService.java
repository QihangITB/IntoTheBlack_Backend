package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.dto.AuthResponseDTO;
import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.RegisterRequestDTO;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.repository.UserRepository;
import com.intotheblack.itb_api.util.GlobalMessage;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request) {
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.USERNAME_REQUIRED);
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.PASSWORD_REQUIRED);
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, GlobalMessage.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, GlobalMessage.USER_NOT_FOUND));

        String token = jwtService.getToken(user);
        
        return new AuthResponseDTO(token, user);
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.USERNAME_REQUIRED);
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.PASSWORD_REQUIRED);
        }

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.EMAIL_REQUIRED);
        }

        if (!isValidEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GlobalMessage.INVALID_EMAIL_FORMAT);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, GlobalMessage.USERNAME_TAKEN);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, GlobalMessage.EMAIL_IN_USE);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        userRepository.save(user);

        String token = jwtService.getToken(user);

        return new AuthResponseDTO(token, user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}