package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.service.FragmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fragments")
@Tag(name = "Fragments", description = "Endpoints para gestionar los fragmentos de coleccionable")
public class FragmentController {
    private final FragmentService fragmentService;

    @Autowired
    public FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fragment> getFragmentById(@PathVariable Long id) {
        return ResponseEntity.ok(fragmentService.findFragmentById(id));
    }

    @GetMapping("/all-in-order")
    public ResponseEntity<List<Fragment>> getAllFragmentsInOrder() {
        return ResponseEntity.ok(fragmentService.findAllFragmentsInOrder());
    }

    @PostMapping()
    public ResponseEntity<Fragment> createNewFragment(@RequestBody Fragment fragment) {
        Fragment newFragment = fragmentService.createFragment(fragment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFragment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFragmentById(@PathVariable Long id) {
        fragmentService.deleteFragmentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Fragmento eliminado con éxito");
    }

    @PutMapping("/{id}/message")
    public ResponseEntity<Fragment> updateMessageById(@PathVariable Long id, @RequestBody String message) {
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
