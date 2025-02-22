package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
import com.intotheblack.itb_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByUsername_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.findUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testFindUserByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.findUserByUsername(username);
        });

        assertEquals("User not found with username: " + username, exception.getMessage());
    }

    @Test
    void testFindUserPlayersByUsername_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        PlayersResponseDTO result = userService.findUserPlayersByUsername(username);

        assertNotNull(result);
    }

    @Test
    void testFindUserPlayersByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        PlayersResponseDTO result = userService.findUserPlayersByUsername(username);

        assertNotNull(result);
    }

    @Test
    void testChangePasswordWithUsername_Success() {
        String username = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        PasswordRequestDTO request = new PasswordRequestDTO(oldPassword, newPassword);
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedOldPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        boolean result = userService.changePasswordWithUsername(username, request);

        assertTrue(result);
        verify(userRepository).save(user);
    }

    @Test
    void testChangePasswordWithUsername_UserNotFound() {
        String username = "testUser";
        PasswordRequestDTO request = new PasswordRequestDTO("oldPassword", "newPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = userService.changePasswordWithUsername(username, request);

        assertFalse(result);
    }

    @Test
    void testCheckPasswordWithUsername_Success() {
        String username = "testUser";
        String password = "password";
        LoginRequestDTO login = new LoginRequestDTO(username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        boolean result = userService.checkPasswordWithUsername(login);

        assertTrue(result);
    }

    @Test
    void testCheckPasswordWithUsername_UserNotFound() {
        String username = "testUser";
        String password = "password";
        LoginRequestDTO login = new LoginRequestDTO(username, password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = userService.checkPasswordWithUsername(login);

        assertFalse(result);
    }

    @Test
    void testDeleteUserByUsername_Success() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setId(1);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean result = userService.deleteUserByUsername(username);

        assertTrue(result);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void testDeleteUserByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = userService.deleteUserByUsername(username);

        assertFalse(result);
    }

    @Test
    void testDeleteUserByUsername_InvalidUsername() {
        String username = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserByUsername(username);
        });

        assertEquals("Username is required", exception.getMessage());
    }
}