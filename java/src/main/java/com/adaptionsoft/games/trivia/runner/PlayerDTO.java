package com.adaptionsoft.games.trivia.runner;


public class PlayerDTO {
    final public String playerName;
    public int goldCoins;
    public int place;
    public boolean isInPenaltyBox;

    public PlayerDTO(String playerName) {
        this.playerName = playerName;
    }
}
