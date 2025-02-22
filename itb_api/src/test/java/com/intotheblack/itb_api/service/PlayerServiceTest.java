package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.*;
import com.intotheblack.itb_api.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private UserService userService;

    @Mock
    private FragmentService fragmentService;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPlayerById() {
        Player player = new Player();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        Player result = playerService.findPlayerById(1);

        assertNotNull(result);
        assertEquals(player, result);
    }

    @Test
    void testFindPlayerById_NotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            playerService.findPlayerById(1);
        });

        assertEquals("Player not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreatePlayer() {
        User user = new User();
        when(userService.findUserByUsername("username")).thenReturn(user);

        Player player = new Player();
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.createPlayer("username");

        assertNotNull(result);
        assertEquals(player, result);
    }

    @Test
    void testResetPlayerDataById() {
        Player player = new Player();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        boolean result = playerService.resetPlayerDataById(1);

        assertTrue(result);
    }

    @Test
    void testResetPlayerDataById_NotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = playerService.resetPlayerDataById(1);

        assertFalse(result);
    }

    @Test
    void testChangeRecordTimeById() {
        Player player = new Player();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.changeRecordTimeById(1, "01:23:45");

        assertNotNull(result);
        assertEquals("01:23:45", result.getRecordTime());
    }

    @Test
    void testAddFragmentToListById() {
        Player player = new Player();
        player.setFragmentList(new ArrayList<>());
        Fragment fragment = new Fragment();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(fragmentService.findFragmentById(1)).thenReturn(fragment);
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.addFragmentToListById(1, 1);

        assertNotNull(result);
        assertTrue(result.getFragmentList().contains(fragment));
    }

    @Test
    void testDeletePlayerById() {
        Player player = new Player();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        boolean result = playerService.deletePlayerById(1);

        assertTrue(result);
        verify(playerRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePlayerById_NotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = playerService.deletePlayerById(1);

        assertFalse(result);
    }

    @Test
    void testDeletePlayerById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.deletePlayerById(null);
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testDeletePlayerById_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.deletePlayerById(-1);
        });

        assertEquals("Id cannot be negative", exception.getMessage());
    }

    @Test
    void testChangeRecordTimeById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.changeRecordTimeById(null, "01:23:45");
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testChangeRecordTimeById_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.changeRecordTimeById(-1, "01:23:45");
        });

        assertEquals("Id cannot be negative", exception.getMessage());
    }

    @Test
    void testChangeRecordTimeById_NullRecordTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.changeRecordTimeById(1, null);
        });

        assertEquals("Record time is required", exception.getMessage());
    }

    @Test
    void testAddFragmentToListById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.addFragmentToListById(null, 1);
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testAddFragmentToListById_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.addFragmentToListById(-1, 1);
        });

        assertEquals("Id cannot be negative", exception.getMessage());
    }

    @Test
    void testAddFragmentToListById_NullFragmentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.addFragmentToListById(1, null);
        });

        assertEquals("Fragment Id is required", exception.getMessage());
    }

    @Test
    void testAddFragmentToListById_NegativeFragmentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.addFragmentToListById(1, -1);
        });

        assertEquals("Fragment Id cannot be negative", exception.getMessage());
    }

    @Test
    void testAddFragmentToListById_NotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());
        when(fragmentService.findFragmentById(1)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            playerService.addFragmentToListById(1, 1);
        });

        assertEquals("Player/Fragment not found with id: 1", exception.getMessage());
    }

    @Test
    void testChangeRecordTimeById_InvalidRecordTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.changeRecordTimeById(1, "1234");
        });

        assertEquals("Invalid record time format. Expected hh:mm:ss", exception.getMessage());
    }

    @Test
    void testChangeRecordTimeById_PlayerNotFound() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.changeRecordTimeById(1, "01:23:45");
        });

        assertEquals("Player not found with id: 1", exception.getMessage());
    }
}