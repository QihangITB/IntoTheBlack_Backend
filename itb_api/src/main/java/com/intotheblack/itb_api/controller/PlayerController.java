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
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer playerId) {
        return ResponseEntity.ok(playerService.findPlayerById(playerId));
    }

    @Operation(summary = "Crear un nuevo perfil de jugador")
    @PostMapping()
    public ResponseEntity<Player> createNewPlayer(@RequestParam String username) {
        Player newPlayer = playerService.createPlayer(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @Operation(summary = "Restablecer los datos a los valores iniciales")
    @PutMapping("/{playerId}/reset-data")
    public ResponseEntity<String> resetPlayerDataById(@PathVariable Integer playerId) {
        boolean success = playerService.resetPlayerDataById(playerId);
        if (success) {
            return new ResponseEntity<>("Datos restablecidos con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar tiempo de finalziación del jugador a través del id")
    @PutMapping("/{playerId}/time")
    public ResponseEntity<Player> updateTimeById(
        @PathVariable Integer playerId, 
        @RequestParam String recordTime) {
        Player player = playerService.changeRecordTimeById(playerId, recordTime);
        return ResponseEntity.ok(player);
    }

    @Operation(summary = "Añadir nuevo fragmento de colección a jugador utilizando los id's")
    @PutMapping("/{playerId}/fragment/add/{fragmentId}")
    public ResponseEntity<Player> addFragmentToPlayerById(
        @PathVariable Integer playerId, 
        @PathVariable Integer fragmentId) {
        Player player = playerService.addFragmentToListById(playerId, fragmentId);
        return ResponseEntity.ok(player);
    }

    @Operation(summary = "Eliminar un jugador a través del id")
    @DeleteMapping("/{playerId}")
    public ResponseEntity<String> deletePlayerById(@PathVariable Integer playerId) {
        boolean success = playerService.deletePlayerById(playerId);
        if (success) {
            return new ResponseEntity<>("Jugador eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
