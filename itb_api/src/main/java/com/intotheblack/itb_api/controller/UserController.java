package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.UserDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints para gestionar los usuarios")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener usuario a través del nombre")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Cambiar la contraseña de un usuario")
    @PutMapping("/password/change/{username}")
    public ResponseEntity<String> changePassword(
            @PathVariable String username, 
            @RequestBody PasswordRequestDTO passwordRequest) {
        boolean success = userService.changePasswordWithUsername(username, passwordRequest);
        if (success) {
            return new ResponseEntity<>("Contraseña cambiada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al cambiar la contraseña.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Comprobar contraseña de un usuario")
    @GetMapping("/password/check/{username}")
    public ResponseEntity<String> checkPassword(
            @PathVariable String username, 
            @RequestParam String password) {
        boolean success = userService.checkPasswordWithUsername(username, password);
        if (success) {
            return new ResponseEntity<>("Contraseña correcta.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Contraseña incorrecta.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Eliminar un usuario a través de ID")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByName(@PathVariable String username) {
        boolean success = userService.deleteUserByUsername(username);
        if (success) {
            return new ResponseEntity<>("Usuario eliminado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado o error al eliminar.", HttpStatus.NOT_FOUND);
        }
    }
}
