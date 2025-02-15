package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.FragmentDTO;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.service.FragmentService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Fragment> getFragmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(fragmentService.findFragmentById(id));
    }

    @GetMapping("/all-in-order")
    public ResponseEntity<List<Fragment>> getAllFragmentsInOrder() {
        return ResponseEntity.ok(fragmentService.findAllFragmentsInOrder());
    }

    @PostMapping()
    public ResponseEntity<Fragment> createNewFragment(@RequestBody FragmentDTO fragment) {
        Fragment newFragment = fragmentService.createFragment(fragment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFragment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFragmentById(@PathVariable Integer id) {
        fragmentService.deleteFragmentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Fragmento eliminado con éxito");
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Fragment> updateMessageById(
        @PathVariable Integer id, 
        @RequestBody MessageRequestDTO message) {
        try {
            Fragment updatedFragment = fragmentService.changeMessageById(id, message);
            return ResponseEntity.ok(updatedFragment); // Devuelve el fragmento actualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Error por mensaje inválido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Error del servidor
        }
    }

}
