package com.intotheblack.itb_api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.intotheblack.itb_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConnectionServiceTest {

    @InjectMocks
    private ConnectionService connectionService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsDatabaseConnected_ReturnsTrue() {
        when(userRepository.count()).thenReturn(1L);
        assertTrue(connectionService.isDatabaseConnected());
    }

    @Test
    void testIsDatabaseConnected_ReturnsFalse() {
        when(userRepository.count()).thenThrow(new RuntimeException("Database not reachable"));
        assertFalse(connectionService.isDatabaseConnected());
    }
}