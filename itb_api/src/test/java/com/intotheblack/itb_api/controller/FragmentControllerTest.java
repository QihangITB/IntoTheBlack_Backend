package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.FragmentRequestDTO;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.service.FragmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FragmentControllerTest {

    @Mock
    private FragmentService fragmentService;

    @InjectMocks
    private FragmentController fragmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFragmentById_Success() {
        Fragment fragment = new Fragment();
        when(fragmentService.findFragmentById(1)).thenReturn(fragment);

        ResponseEntity<Object> response = fragmentController.getFragmentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fragment, response.getBody());
    }

    @Test
    void testGetFragmentById_NotFound() {
        when(fragmentService.findFragmentById(1)).thenThrow(new RuntimeException("Fragment not found"));

        ResponseEntity<Object> response = fragmentController.getFragmentById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fragment not found", response.getBody());
    }

    @Test
    void testGetAllFragmentsInOrder_Success() {
        List<Fragment> fragments = Arrays.asList(new Fragment(), new Fragment());
        when(fragmentService.findAllFragmentsInOrder()).thenReturn(fragments);

        ResponseEntity<Object> response = fragmentController.getAllFragmentsInOrder();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fragments, response.getBody());
    }

    @Test
    void testCreateNewFragment_Success() {
        FragmentRequestDTO fragmentRequestDTO = new FragmentRequestDTO();
        Fragment fragment = new Fragment();
        when(fragmentService.createFragment(fragmentRequestDTO)).thenReturn(fragment);

        ResponseEntity<Object> response = fragmentController.createNewFragment(fragmentRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fragment, response.getBody());
    }

    @Test
    void testUpdateMessageById_Success() {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        Fragment fragment = new Fragment();
        when(fragmentService.changeMessageById(1, messageRequestDTO)).thenReturn(fragment);

        ResponseEntity<Object> response = fragmentController.updateMessageById(1, messageRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fragment, response.getBody());
    }

    @Test
    void testDeleteFragmentById_Success() {
        when(fragmentService.deleteFragmentById(1)).thenReturn(true);

        ResponseEntity<String> response = fragmentController.deleteFragmentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fragment deleted successfully", response.getBody());
    }

    @Test
    void testDeleteFragmentById_NotFound() {
        when(fragmentService.deleteFragmentById(1)).thenReturn(false);

        ResponseEntity<String> response = fragmentController.deleteFragmentById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Fragment not deleted", response.getBody());
    }
}