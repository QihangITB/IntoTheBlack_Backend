package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.service.FragmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fragments")
@Tag(name = "Fragments", description = "Endpoints para gestionar los fragmentos de coleccionable")
public class FragmentController {
    private final FragmentService fragmentService;

    @Autowired
    public FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Fragment> getFragmentById(@PathVariable Long id) {
        return ResponseEntity.ok(fragmentService.findFragmentById(id));
    }

    @GetMapping("/find/all-in-order")
    public ResponseEntity<List<Fragment>> getAllFragments() {
        return ResponseEntity.ok(fragmentService.findAllFragmentsInOrder());
    }

    @PostMapping("/create")
    public ResponseEntity<Fragment> postNewFragment(@RequestBody Fragment fragment) {
        Fragment newFragment = fragmentService.createFragment(fragment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFragment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFragment(@PathVariable Long id) {
        fragmentService.deleteFragment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Fragmento eliminado con éxito");
    }

}
