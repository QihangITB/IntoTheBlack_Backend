// package com.intotheblack.itb_api.controller;

// import com.intotheblack.itb_api.model.Player;
// import com.intotheblack.itb_api.service.PlayerService;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/players")
// @Tag(name = "Players", description = "Endpoints para gestionar los perfiles de jugadores")
// public class PlayerController {
//     private final PlayerService playerService;

//     public PlayerController(PlayerService playerService) {
//         this.playerService = playerService;
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
//         return ResponseEntity.ok(playerService.findPlayerById(id));
//     }

//     @GetMapping("/username/{username}")
//     public ResponseEntity<Player> getPlayerByUsername(@PathVariable String username) {
//         return ResponseEntity.ok(playerService.findPlayerByUsername(username));
//     }

//     @PostMapping()
//     public ResponseEntity<Player> createNewPlayer(@RequestBody Player player) {
//         Player newPlayer = playerService.createPlayer(player);
//         return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deletePlayerById(@PathVariable String id) {
//         playerService.deletePlayerById(id);
//         return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Jugador eliminado con éxito");
//     }
// }
