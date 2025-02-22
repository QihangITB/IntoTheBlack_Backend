package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.FragmentRequestDTO;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.service.FragmentService;
import com.intotheblack.itb_api.util.GlobalMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fragments")
@Tag(name = "Fragments", description = "Endpoints para gestionar los fragmentos de coleccionable")
public class FragmentController {
    private final FragmentService fragmentService;

    public FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }

    @Operation(summary = "Obtener fragmento a través del id")
    @GetMapping("/{fragmentId}")
    public ResponseEntity<Object> getFragmentById(@PathVariable Integer fragmentId) {
        try {
            Fragment fragment = fragmentService.findFragmentById(fragmentId);
            return ResponseEntity.ok(fragment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener todos los fragmentos en orden (no por orden de id)")
    @GetMapping("/all-in-order")
    public ResponseEntity<Object> getAllFragmentsInOrder() {
        try {
            List<Fragment> fragments = fragmentService.findAllFragmentsInOrder();
            return ResponseEntity.ok(fragments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }    
    }

    @Operation(summary = "Crear un nuevo fragmento")
    @PostMapping()
    public ResponseEntity<Object> createNewFragment(@RequestBody FragmentRequestDTO fragment) {
        try {
            Fragment newFragment = fragmentService.createFragment(fragment);
            return ResponseEntity.status(HttpStatus.CREATED).body(newFragment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar el mensaje de un fragmento a través del id")
    @PutMapping("/{fragmentId}/message")
    public ResponseEntity<Object> updateMessageById(
        @PathVariable Integer fragmentId, 
        @RequestBody MessageRequestDTO message) {
            try {
                Fragment updatedFragment = fragmentService.changeMessageById(fragmentId, message);
                return ResponseEntity.ok(updatedFragment);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
            }
    }

    @Operation(summary = "Eliminar un fragmento a través del id")
    @DeleteMapping("/{fragmentId}")
    public ResponseEntity<String> deleteFragmentById(@PathVariable Integer fragmentId) {
        try {
            boolean success = fragmentService.deleteFragmentById(fragmentId);
            if(success) {
                return new ResponseEntity<>(GlobalMessage.FRAGMENT_DELETED_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(GlobalMessage.FRAGMENT_DELETION_FAILED, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }
}
