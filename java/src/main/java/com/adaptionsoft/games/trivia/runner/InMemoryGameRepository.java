package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.PlayerAdded;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;
    private List<String> players = new ArrayList<>();

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame;
    }

    @Override
    public Game getGame() {
        //TODO générer une instance de Game au lieu d'utiliser le field
        return aGame;
    }

    @Override
    public void save(PlayerAdded playerAddedEvent) {
        // insert the player into players table
        players.add(playerAddedEvent.playerName);
    }
}
