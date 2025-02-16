package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.UserRegisterDTO;
import com.intotheblack.itb_api.dto.UserLoginDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
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

    @Operation(summary = "Obtener jugadores de un usuario a través del nombre")
    @GetMapping("/{username}/players")
    public ResponseEntity<PlayersResponseDTO> getPlayersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findPlayersByUsername(username));
    }

    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegisterDTO user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    
    @Operation(summary = "Iniciar sessión con un usuario")
    @PostMapping("/login")
    public ResponseEntity<String> checkPassword(@RequestBody UserLoginDTO login) {
        boolean success = userService.checkPasswordWithUsername(login);
        if (success) {
            return new ResponseEntity<>("Sessión iniciada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al iniciar sessión.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Cambiar la contraseña de un usuario")
    @PutMapping("/{username}/password/change")
    public ResponseEntity<String> changePassword(
            @PathVariable String username, 
            @RequestBody PasswordRequestDTO passwordRequest) {
        boolean success = userService.changePasswordWithUsername(username, passwordRequest);
        if (success) {
            return new ResponseEntity<>("Contraseña cambiada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error al cambiar la contraseña", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Eliminar un usuario a través del nombre")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByName(@PathVariable String username) {
        boolean success = userService.deleteUserByUsername(username);
        if (success) {
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
