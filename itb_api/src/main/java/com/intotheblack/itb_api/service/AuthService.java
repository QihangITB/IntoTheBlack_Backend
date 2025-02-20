package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.dto.AuthResponseDTO;
import com.intotheblack.itb_api.dto.UserLoginDTO;
import com.intotheblack.itb_api.dto.UserRegisterDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(UserLoginDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        
        return new AuthResponseDTO(token, user);
    }

    public AuthResponseDTO register(UserRegisterDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        userRepository.save(user);

        String token = jwtService.getToken(user);

        return new AuthResponseDTO(token, user);
    }
    
}
