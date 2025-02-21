package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.dto.AuthResponseDTO;
import com.intotheblack.itb_api.dto.UserLoginDTO;
import com.intotheblack.itb_api.dto.UserRegisterDTO;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_UsernameRequired() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setPassword("password");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals("400 BAD_REQUEST \"Username is required\"", exception.getMessage());
    }

    @Test
    void testLogin_PasswordRequired() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("testuser");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals("400 BAD_REQUEST \"Password is required\"", exception.getMessage());
    }

    @Test
    void testLogin_InvalidCredentials() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("wrongpassword");

        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals("401 UNAUTHORIZED \"Invalid credentials\"", exception.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn("token");

        AuthResponseDTO response = authService.login(loginDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(user, response.getUser());
    }

    @Test
    void testRegister_Success() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("newuser@test.com");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("newuser@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedpassword");
        when(jwtService.getToken(any(User.class))).thenReturn("token");

        AuthResponseDTO response = authService.register(registerDTO);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals("newuser", response.getUser().getUsername());
        assertEquals("newuser@test.com", response.getUser().getEmail());
    }

    @Test
    void testRegister_UsernameRequired() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setPassword("password");
        registerDTO.setEmail("test@test.com");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("400 BAD_REQUEST \"Username is required\"", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_PasswordRequired() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setEmail("test@test.com");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("400 BAD_REQUEST \"Password is required\"", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_EmailRequired() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setPassword("password");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("400 BAD_REQUEST \"Email is required\"", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_InvalidEmail() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("invalidemail");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("400 BAD_REQUEST \"Invalid email format\"", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_UsernameTaken() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("existinguser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("newuser@test.com");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("409 CONFLICT \"Username is already taken\"", exception.getMessage());
    }

    @Test
    void testRegister_EmailInUse() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("newuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("existingemail@test.com");

        when(userRepository.findByEmail("existingemail@test.com")).thenReturn(Optional.of(new User()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("409 CONFLICT \"Email is already in use\"", exception.getMessage());
    }
}