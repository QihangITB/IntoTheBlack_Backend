package com.intotheblack.itb_api.dto;

import com.intotheblack.itb_api.model.Player;
import java.util.List;

public class PlayersResponseDTO {

    private List<Player> players;

    // Constructor
    public PlayersResponseDTO() {
    }

    public PlayersResponseDTO(List<Player> players) {
        this.players = players;
    }

    // Getters y setters
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
