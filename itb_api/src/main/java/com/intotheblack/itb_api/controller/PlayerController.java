package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.service.PlayerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@Tag(name = "Players", description = "Endpoints para gestionar jugadores")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Player> getPlayerByUsername(@PathVariable String username) {
        return ResponseEntity.ok(playerService.findPlayerByUsername(username));
    }

    @PostMapping("/create")
    public ResponseEntity<Player> postNewPlayer(@RequestBody Player player) {
        Player newPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Jugador eliminado con éxito");
    }
}
