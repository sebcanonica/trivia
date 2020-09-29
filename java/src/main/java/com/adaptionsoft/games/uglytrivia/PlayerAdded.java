package com.adaptionsoft.games.uglytrivia;

public class PlayerAdded {
    public final String playerName;
    public final int totalNumberOfPlayers;

    public PlayerAdded(String playerName, int totalNumberOfPlayers) {
        this.playerName = playerName;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }
}
