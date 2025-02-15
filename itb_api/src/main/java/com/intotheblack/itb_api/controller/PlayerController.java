package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.dto.PlayerDTO;
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
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    @Operation(summary = "Crear un nuevo perfil de jugador")
    @PostMapping()
    public ResponseEntity<Player> createNewPlayer(@RequestBody PlayerDTO player) {
        Player newPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }




    
    @Operation(summary = "Eliminar un jugador a través del id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayerById(@PathVariable Integer id) {
        boolean success = playerService.deletePlayerById(id);
        if (success) {
            return new ResponseEntity<>("Jugador eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
