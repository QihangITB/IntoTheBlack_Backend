package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Player;
import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.PlayerDTO;
import com.intotheblack.itb_api.repository.PlayerRepository;

import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final UserService userService;

    public PlayerService(
        PlayerRepository playerRepository,
        UserService userService) {
        this.playerRepository = playerRepository;
        this.userService = userService;
    }

    // METHODS:
    public Player findPlayerById(Integer id) {
        return this.playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    public Player createPlayer(PlayerDTO request) {
        // Buscar usuario por nombre de usuario
        User user = userService.findUserByUsername(request.getUsername());

        Player player = new Player();
        player.setRecordTime(request.getRecordTime());
        player.setFragmentList(request.getFragmentList());
        player.setUser(user);

        return playerRepository.save(player);
    }

    public boolean resetPlayerData(Integer id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            player.setRecordTime("00:00:00");
            player.setFragmentList(new ArrayList<>());
            playerRepository.save(player);
            return true; // Datos restablecidos con éxito
        }
        return false; // Jugador no encontrado
    }

    public Player changeFragmentList(Integer id, PlayerDTO request){
        Optional<Player> playerOptional = playerRepository.findById(id);

        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            player.setFragmentList(request.getFragmentList());
            return playerRepository.save(player);
        }
        throw new IllegalArgumentException("Jugador no encontrado con id: " + id);
    }

    public boolean deletePlayerById(Integer id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            playerRepository.deleteById(player.getId());
            return true; // Eliminado con éxito
        }
        return false; // Jugador no encontrado
    }
}