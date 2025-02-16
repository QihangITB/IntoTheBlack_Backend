package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.*;
import com.intotheblack.itb_api.repository.PlayerRepository;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserService userService;
    private final FragmentService fragmentService;

    public PlayerService(
        PlayerRepository playerRepository,
        UserService userService,
        FragmentService fragmentService) {
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.fragmentService = fragmentService;
    }

    // METHODS:
    public Player findPlayerById(Integer id) {
        return this.playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    public Player createPlayer(String username) {
        // Buscar usuario por nombre de usuario
        User user = userService.findUserByUsername(username);

        // Creamos un nuevo jugador con valores iniciales
        Player player = initializePlayer(new Player());
        player.setUser(user);

        return playerRepository.save(player);
    }

    public boolean resetPlayerDataById(Integer id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            playerRepository.save(initializePlayer(player));
            return true; // Datos restablecidos con éxito
        }
        return false; // Jugador no encontrado
    }

    private Player initializePlayer(Player player) {
        player.setRecordTime("00:00:00");
        player.setFragmentList(new ArrayList<>());
        return player;
    }

    public Player addFragmentToListById(Integer id, Integer fragmentId){
        Optional<Player> playerOptional = playerRepository.findById(id);
        Fragment fragment = fragmentService.findFragmentById(fragmentId);

        if(playerOptional.isPresent() && fragment != null){
            Player player = playerOptional.get();
            List<Fragment> newFragmentList = player.getFragmentList();
            newFragmentList.add(fragment);
            player.setFragmentList(newFragmentList);
            return playerRepository.save(player);
        }
        throw new IllegalArgumentException("Jugador/Fragmento no encontrado con id: " + id);
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