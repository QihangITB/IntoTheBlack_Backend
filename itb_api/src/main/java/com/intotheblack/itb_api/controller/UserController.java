package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
import com.intotheblack.itb_api.service.UserService;
import com.intotheblack.itb_api.util.GlobalMessage;

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
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.findUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener jugadores de un usuario a través del nombre")
    @GetMapping("/{username}/players")
    public ResponseEntity<Object> getPlayersByUsername(@PathVariable String username) {
        try {
            PlayersResponseDTO players = userService.findUserPlayersByUsername(username);
            return ResponseEntity.ok(players);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }
    
    @Operation(summary = "Verificar la contraseña de un usuario")
    @PostMapping("/password/check")
    public ResponseEntity<String> checkPassword(@RequestBody LoginRequestDTO login) {
        try {
            boolean success = userService.checkPasswordWithUsername(login);
            if (success) {
                return ResponseEntity.ok(GlobalMessage.CORRECT_PASSWORD);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalMessage.INCORRECT_PASSWORD);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }

    @Operation(summary = "Cambiar la contraseña de un usuario")
    @PutMapping("/{username}/password/change")
    public ResponseEntity<String> changePassword(
            @PathVariable String username, 
            @RequestBody PasswordRequestDTO passwordRequest) {
        try {
            boolean success = userService.changePasswordWithUsername(username, passwordRequest);
            if (success) {
                return new ResponseEntity<>(GlobalMessage.PASSWORD_CHANGED_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(GlobalMessage.PASSWORD_CHANGE_FAILED, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un usuario a través del nombre")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByName(@PathVariable String username) {
        try {
            boolean success = userService.deleteUserByUsername(username);
            if (success) {
                return new ResponseEntity<>(GlobalMessage.USER_DELETED_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(GlobalMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalMessage.SERVER_ERROR);
        }
    }
}
