package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByUsername_Success() {
        String username = "testUser";
        User user = new User();
        when(userService.findUserByUsername(username)).thenReturn(user);

        ResponseEntity<Object> response = userController.getUserByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        String username = "testUser";
        when(userService.findUserByUsername(username)).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<Object> response = userController.getUserByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testGetPlayersByUsername_Success() {
        String username = "testUser";
        PlayersResponseDTO players = new PlayersResponseDTO();
        when(userService.findUserPlayersByUsername(username)).thenReturn(players);

        ResponseEntity<Object> response = userController.getPlayersByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(players, response.getBody());
    }

    @Test
    void testCheckPassword_Success() {
        LoginRequestDTO login = new LoginRequestDTO();
        when(userService.checkPasswordWithUsername(login)).thenReturn(true);

        ResponseEntity<String> response = userController.checkPassword(login);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Correct password", response.getBody());
    }

    @Test
    void testCheckPassword_IncorrectPassword() {
        LoginRequestDTO login = new LoginRequestDTO();
        when(userService.checkPasswordWithUsername(login)).thenReturn(false);

        ResponseEntity<String> response = userController.checkPassword(login);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Incorrect password", response.getBody());
    }

    @Test
    void testChangePassword_Success() {
        String username = "testUser";
        PasswordRequestDTO passwordRequest = new PasswordRequestDTO();
        when(userService.changePasswordWithUsername(username, passwordRequest)).thenReturn(true);

        ResponseEntity<String> response = userController.changePassword(username, passwordRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully", response.getBody());
    }

    @Test
    void testChangePassword_Error() {
        String username = "testUser";
        PasswordRequestDTO passwordRequest = new PasswordRequestDTO();
        when(userService.changePasswordWithUsername(username, passwordRequest)).thenReturn(false);

        ResponseEntity<String> response = userController.changePassword(username, passwordRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error changing password", response.getBody());
    }

    @Test
    void testDeleteUserByName_Success() {
        String username = "testUser";
        when(userService.deleteUserByUsername(username)).thenReturn(true);

        ResponseEntity<String> response = userController.deleteUserByName(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void testDeleteUserByName_UserNotFound() {
        String username = "testUser";
        when(userService.deleteUserByUsername(username)).thenReturn(false);

        ResponseEntity<String> response = userController.deleteUserByName(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }
}