package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;
import com.adaptionsoft.games.uglytrivia.PlayerAdded;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;
    private List<String> players = new ArrayList<>();

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame;
    }

    @Override
    public Game getGame() {
         List<Player> players =aGame.getPlayers();
         aGame = new Game(players, aGame);
         return aGame;
    }

    @Override
    public void save(PlayerAdded playerAddedEvent) {
        // insert the player into players table
        players.add(playerAddedEvent.playerName);
    }
}
