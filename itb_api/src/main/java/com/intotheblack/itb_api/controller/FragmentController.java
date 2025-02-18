package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.FragmentDTO;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.service.FragmentService;

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
    public ResponseEntity<Fragment> getFragmentById(@PathVariable Integer fragmentId) {
        return ResponseEntity.ok(fragmentService.findFragmentById(fragmentId));
    }

    @Operation(summary = "Obtener todos los fragmentos en orden (no por orden de id)")
    @GetMapping("/all-in-order")
    public ResponseEntity<List<Fragment>> getAllFragmentsInOrder() {
        return ResponseEntity.ok(fragmentService.findAllFragmentsInOrder());
    }

    @Operation(summary = "Crear un nuevo fragmento")
    @PostMapping()
    public ResponseEntity<Fragment> createNewFragment(@RequestBody FragmentDTO fragment) {
        Fragment newFragment = fragmentService.createFragment(fragment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFragment);
    }

    @Operation(summary = "Actualizar el mensaje de un fragmento a través del id")
    @PutMapping("/{fragmentId}/message")
    public ResponseEntity<Fragment> updateMessageById(
        @PathVariable Integer fragmentId, 
        @RequestBody MessageRequestDTO message) {
        try {
            Fragment updatedFragment = fragmentService.changeMessageById(fragmentId, message);
            return ResponseEntity.ok(updatedFragment); // Devuelve el fragmento actualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Error por mensaje inválido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Error del servidor
        }
    }

    @Operation(summary = "Eliminar un fragmento a través del id")
    @DeleteMapping("/{fragmentId}")
    public ResponseEntity<String> deleteFragmentById(@PathVariable Integer fragmentId) {
        boolean success = fragmentService.deleteFragmentById(fragmentId);
        if(success) {
            return new ResponseEntity<>("Fragmento eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fragmento no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
