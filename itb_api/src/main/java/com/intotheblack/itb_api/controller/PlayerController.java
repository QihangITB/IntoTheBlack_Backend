package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@Tag(name = "Players", description = "Endpoints para gestionar los perfiles de jugadores")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Obtener jugador a través del id")
    @GetMapping("/{playerId}")
    public ResponseEntity<Object> getPlayerById(@PathVariable Integer playerId) {
        try {
            Player player = playerService.findPlayerById(playerId);
            return ResponseEntity.ok(player);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
        }    }

    @Operation(summary = "Crear un nuevo perfil de jugador")
    @PostMapping()
    public ResponseEntity<Object> createNewPlayer(@RequestParam String username) {
        try {
            Player newPlayer = playerService.createPlayer(username);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor"); 
        }
    }

    @Operation(summary = "Restablecer los datos a los valores iniciales")
    @PutMapping("/{playerId}/reset-data")
    public ResponseEntity<String> resetPlayerDataById(@PathVariable Integer playerId) {
        try {
            boolean success = playerService.resetPlayerDataById(playerId);
            if (success) {
                return new ResponseEntity<>("Datos restablecidos con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
        }
    }

    @Operation(summary = "Actualizar tiempo de finalziación del jugador a través del id")
    @PutMapping("/{playerId}/time")
    public ResponseEntity<Object> updateTimeById(
        @PathVariable Integer playerId, 
        @RequestParam String recordTime) {
            try {
                Player player = playerService.changeRecordTimeById(playerId, recordTime);
                return ResponseEntity.ok(player);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
            }
    }

    @Operation(summary = "Añadir nuevo fragmento de colección a jugador utilizando los id's")
    @PutMapping("/{playerId}/fragment/add/{fragmentId}")
    public ResponseEntity<Object> addFragmentToPlayerById(
        @PathVariable Integer playerId, 
        @PathVariable Integer fragmentId) {
            try {
                Player player = playerService.addFragmentToListById(playerId, fragmentId);
                return ResponseEntity.ok(player);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
            }
    }

    @Operation(summary = "Eliminar un jugador a través del id")
    @DeleteMapping("/{playerId}")
    public ResponseEntity<String> deletePlayerById(@PathVariable Integer playerId) {
        try {
            boolean success = playerService.deletePlayerById(playerId);
            if (success) {
                return new ResponseEntity<>("Jugador eliminado con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
        }
    }
}
