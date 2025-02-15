// package com.intotheblack.itb_api.service;

// import com.intotheblack.itb_api.model.Player;
// import com.intotheblack.itb_api.repository.PlayerRepository;
// import org.springframework.stereotype.Service;

// @Service
// public class PlayerService {
//     private final PlayerRepository playerRepository;

//     public PlayerService(PlayerRepository playerRepository) {
//         this.playerRepository = playerRepository;
//     }

//     // METHODS:

//     public Player findPlayerById(String id) {
//         return this.playerRepository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
//     }

//     public Player findPlayerByUsername(String username) {
//         return this.playerRepository.findByUsername(username)
//             .orElseThrow(() -> new RuntimeException("Jugador no encontrado con username: " + username));
//     }

//     public Player createPlayer(Player player) {
//         return playerRepository.save(player);
//     }

//     public void deletePlayerById(String id) {
//         if (!playerRepository.existsById(id)) {
//             throw new RuntimeException("Jugador no encontrado con ID: " + id);
//         }
//         playerRepository.deleteById(id);
//     }
// }