package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlayerById_Success() {
        Player player = new Player();
        when(playerService.findPlayerById(anyInt())).thenReturn(player);

        ResponseEntity<Object> response = playerController.getPlayerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testGetPlayerById_NotFound() {
        when(playerService.findPlayerById(anyInt())).thenThrow(new RuntimeException("Jugador no encontrado"));

        ResponseEntity<Object> response = playerController.getPlayerById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Jugador no encontrado", response.getBody());
    }

    @Test
    void testCreateNewPlayer_Success() {
        Player player = new Player();
        when(playerService.createPlayer(anyString())).thenReturn(player);

        ResponseEntity<Object> response = playerController.createNewPlayer("username");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testResetPlayerDataById_Success() {
        when(playerService.resetPlayerDataById(anyInt())).thenReturn(true);

        ResponseEntity<String> response = playerController.resetPlayerDataById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Datos restablecidos con éxito", response.getBody());
    }

    @Test
    void testResetPlayerDataById_NotFound() {
        when(playerService.resetPlayerDataById(anyInt())).thenReturn(false);

        ResponseEntity<String> response = playerController.resetPlayerDataById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Jugador no encontrado", response.getBody());
    }

    @Test
    void testUpdateTimeById_Success() {
        Player player = new Player();
        when(playerService.changeRecordTimeById(anyInt(), anyString())).thenReturn(player);

        ResponseEntity<Object> response = playerController.updateTimeById(1, "00:00:12");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testAddFragmentToPlayerById_Success() {
        Player player = new Player();
        when(playerService.addFragmentToListById(anyInt(), anyInt())).thenReturn(player);

        ResponseEntity<Object> response = playerController.addFragmentToPlayerById(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testDeletePlayerById_Success() {
        when(playerService.deletePlayerById(anyInt())).thenReturn(true);

        ResponseEntity<String> response = playerController.deletePlayerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jugador eliminado con éxito", response.getBody());
    }

    @Test
    void testDeletePlayerById_NotFound() {
        when(playerService.deletePlayerById(anyInt())).thenReturn(false);

        ResponseEntity<String> response = playerController.deletePlayerById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Jugador no encontrado", response.getBody());
    }

    @Test
    void testDeletePlayerById_InvalidId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).deletePlayerById(-1);

        ResponseEntity<String> response = playerController.deletePlayerById(-1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }

    @Test
    void testAddFragmentToPlayerById_NotFound() {
        when(playerService.addFragmentToListById(anyInt(), anyInt())).thenThrow(new RuntimeException("Jugador o fragmento no encontrado"));

        ResponseEntity<Object> response = playerController.addFragmentToPlayerById(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Jugador o fragmento no encontrado", response.getBody());
    }

    @Test
    void testDeletePlayerById_Exception() {
        when(playerService.deletePlayerById(anyInt())).thenThrow(new RuntimeException("Error del servidor"));

        ResponseEntity<String> response = playerController.deletePlayerById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error del servidor", response.getBody());
    }

    @Test
    void testCreateNewPlayer_Exception() {
        when(playerService.createPlayer(anyString())).thenThrow(new RuntimeException("Error del servidor"));

        ResponseEntity<Object> response = playerController.createNewPlayer("username");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error del servidor", response.getBody());
    }

    @Test
    void testResetPlayerDataById_Exception() {
        when(playerService.resetPlayerDataById(anyInt())).thenThrow(new RuntimeException("Error del servidor"));

        ResponseEntity<String> response = playerController.resetPlayerDataById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error del servidor", response.getBody());
    }

    @Test
    void testUpdateTimeById_NotFound() {
        when(playerService.changeRecordTimeById(anyInt(), anyString())).thenThrow(new RuntimeException("Jugador no encontrado"));

        ResponseEntity<Object> response = playerController.updateTimeById(1, "00:00:12");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Jugador no encontrado", response.getBody());
    }

    @Test
    void testGetPlayerById_InvalidId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).findPlayerById(0);

        ResponseEntity<Object> response = playerController.getPlayerById(0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }

    @Test
    void testCreateNewPlayer_InvalidUsername() {
        doThrow(new IllegalArgumentException("Username is required")).when(playerService).createPlayer("");

        ResponseEntity<Object> response = playerController.createNewPlayer("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username is required", response.getBody());
    }

    @Test
    void testCreateNewPlayer_InvalidUsername_Exception() {
        when(playerService.createPlayer("username")).thenThrow(new RuntimeException("Error del servidor"));

        ResponseEntity<Object> response = playerController.createNewPlayer("username");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error del servidor", response.getBody());
    }

    @Test
    void testResetPlayerDataById_InvalidId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).resetPlayerDataById(0);

        ResponseEntity<String> response = playerController.resetPlayerDataById(0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }

    @Test
    void testUpdateTimeById_InvalidId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).changeRecordTimeById(-1, "recordTime");

        ResponseEntity<Object> response = playerController.updateTimeById(-1, "00:00:12");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }

    @Test
    void testUpdateTimeById_InvalidRecordTime() {
        doThrow(new IllegalArgumentException("Record time is required")).when(playerService).changeRecordTimeById(1, "");

        ResponseEntity<Object> response = playerController.updateTimeById(1, "");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Record time is required", response.getBody());
    }

    @Test
    void testAddFragmentToPlayerById_InvalidId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).addFragmentToListById(0, 1);

        ResponseEntity<Object> response = playerController.addFragmentToPlayerById(0, 1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }

    @Test
    void testAddFragmentToPlayerById_InvalidFragmentId() {
        doThrow(new IllegalArgumentException("Id cannot be negative")).when(playerService).addFragmentToListById(1, 0);

        ResponseEntity<Object> response = playerController.addFragmentToPlayerById(1, 0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id cannot be negative", response.getBody());
    }
}