package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.*;
import com.intotheblack.itb_api.repository.PlayerRepository;
import com.intotheblack.itb_api.util.GlobalMessage;

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
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
        return this.playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(GlobalMessage.PLAYER_NOT_FOUND + id));
    }

    public Player createPlayer(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException(GlobalMessage.USERNAME_REQUIRED);
        }
    
        User user = userService.findUserByUsername(username);
        Player player = initializePlayer(new Player());
        player.setUser(user);
    
        return playerRepository.save(player);
    }

    public boolean resetPlayerDataById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }

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

    public Player changeRecordTimeById(Integer id, String recordTime) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
        if (recordTime == null || recordTime.isEmpty()) {
            throw new IllegalArgumentException(GlobalMessage.RECORD_TIME_REQUIRED);
        }

        // Expresión regular para el formato hh:mm:ss
        String timePattern = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
        if (!recordTime.matches(timePattern)) {
            throw new IllegalArgumentException(GlobalMessage.INVALID_RECORD_TIME_FORMAT);
        }
    
        Optional<Player> playerOptional = playerRepository.findById(id);
    
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setRecordTime(recordTime);
            return playerRepository.save(player);
        }
        throw new IllegalArgumentException(GlobalMessage.PLAYER_NOT_FOUND + id);
    }

    public Player addFragmentToListById(Integer id, Integer fragmentId) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
        if (fragmentId == null) {
            throw new IllegalArgumentException(GlobalMessage.FRAGMENT_ID_REQUIRED);
        }
        if (fragmentId < 0) {
            throw new IllegalArgumentException(GlobalMessage.FRAGMENT_ID_NEGATIVE);
        }
    
        Optional<Player> playerOptional = playerRepository.findById(id);
        Fragment fragment = fragmentService.findFragmentById(fragmentId);
    
        if (playerOptional.isPresent() && fragment != null) {
            Player player = playerOptional.get();
            List<Fragment> newFragmentList = player.getFragmentList();
            newFragmentList.add(fragment);
            player.setFragmentList(newFragmentList);
            return playerRepository.save(player);
        }
        throw new IllegalArgumentException(GlobalMessage.PLAYER_FRAGMENT_NOT_FOUND + id);
    }

    public boolean deletePlayerById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
    
        Optional<Player> playerOptional = playerRepository.findById(id);
    
        if (playerOptional.isPresent()) {
            playerRepository.deleteById(id);
            return true; // Eliminado con éxito
        }
        return false; // Jugador no encontrado
    }
}