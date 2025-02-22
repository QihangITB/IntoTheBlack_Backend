package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.dto.FragmentRequestDTO;
import com.intotheblack.itb_api.repository.FragmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FragmentServiceTest {

    @Mock
    private FragmentRepository fragmentRepository;

    @InjectMocks
    private FragmentService fragmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindFragmentById_Success() {
        Fragment fragment = new Fragment();
        fragment.setId(1);
        when(fragmentRepository.findById(1)).thenReturn(Optional.of(fragment));

        Fragment result = fragmentService.findFragmentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testFindFragmentById_NotFound() {
        when(fragmentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fragmentService.findFragmentById(1);
        });

        assertEquals("Fragment not found with id: 1", exception.getMessage());
    }

    @Test
    void testFindAllFragmentsInOrder_Success() {
        Fragment fragment1 = new Fragment();
        Fragment fragment2 = new Fragment();
        when(fragmentRepository.findAllOrderByOrderNumber()).thenReturn(Optional.of(Arrays.asList(fragment1, fragment2)));

        List<Fragment> result = fragmentService.findAllFragmentsInOrder();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllFragmentsInOrder_Empty() {
        when(fragmentRepository.findAllOrderByOrderNumber()).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fragmentService.findAllFragmentsInOrder();
        });

        assertEquals("Fragment list is empty", exception.getMessage());
    }

    @Test
    void testCreateFragment_Success() {
        FragmentRequestDTO request = new FragmentRequestDTO();
        request.setOrderNumber(1);
        request.setMessage("Test message");

        Fragment fragment = new Fragment();
        fragment.setOrderNumber(1);
        fragment.setMessage("Test message");

        when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);

        Fragment result = fragmentService.createFragment(request);

        assertNotNull(result);
        assertEquals(1, result.getOrderNumber());
        assertEquals("Test message", result.getMessage());
    }

    @Test
    void testChangeMessageById_Success() {
        MessageRequestDTO request = new MessageRequestDTO();
        request.setMessage("Updated message");

        Fragment fragment = new Fragment();
        fragment.setId(1);
        fragment.setMessage("Old message");

        when(fragmentRepository.findById(1)).thenReturn(Optional.of(fragment));
        when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);

        Fragment result = fragmentService.changeMessageById(1, request);

        assertNotNull(result);
        assertEquals("Updated message", result.getMessage());
    }

    @Test
    void testDeleteFragmentById_Success() {
        Fragment fragment = new Fragment();
        fragment.setId(1);

        when(fragmentRepository.findById(1)).thenReturn(Optional.of(fragment));

        boolean result = fragmentService.deleteFragmentById(1);

        assertTrue(result);
        verify(fragmentRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFragmentById_NotFound() {
        when(fragmentRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = fragmentService.deleteFragmentById(1);

        assertFalse(result);
        verify(fragmentRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteFragmentById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.deleteFragmentById(null);
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testDeleteFragmentById_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.deleteFragmentById(-1);
        });

        assertEquals("Id cannot be negative", exception.getMessage());
    }

    @Test
    void testChangeMessageById_NullId() {
        MessageRequestDTO request = new MessageRequestDTO();
        request.setMessage("Updated message");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.changeMessageById(null, request);
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testChangeMessageById_NegativeId() {
        MessageRequestDTO request = new MessageRequestDTO();
        request.setMessage("Updated message");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.changeMessageById(-1, request);
        });
    
        assertEquals("Id cannot be negative", exception.getMessage());
    }

    @Test
    void testChangeMessageById_NullRequest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.changeMessageById(1, null);
        });

        assertEquals("Request cannot be null", exception.getMessage());
    }

    @Test
    void testChangeMessageById_NullMessage() {
        MessageRequestDTO request = new MessageRequestDTO();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.changeMessageById(1, request);
        });

        assertEquals("Message is required", exception.getMessage());
    }

    @Test
    void testCreateFragment_NullRequest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.createFragment(null);
        });

        assertEquals("Request cannot be null", exception.getMessage());
    }

    @Test
    void testCreateFragment_NullOrderNumber() {
        FragmentRequestDTO request = new FragmentRequestDTO();
        request.setMessage("Test message");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.createFragment(request);
        });

        assertEquals("Order number is required", exception.getMessage());
    }

    @Test
    void testCreateFragment_NullMessage() {
        FragmentRequestDTO request = new FragmentRequestDTO();
        request.setOrderNumber(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.createFragment(request);
        });

        assertEquals("Message is required", exception.getMessage());
    }

    @Test
    void testFindFragmentById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.findFragmentById(null);
        });

        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    void testFindFragmentById_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fragmentService.findFragmentById(-1);
        });

        assertEquals("Id cannot be negative", exception.getMessage());
    }
}