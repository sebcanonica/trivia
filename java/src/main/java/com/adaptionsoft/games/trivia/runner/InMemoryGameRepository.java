package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.GoldCoinWon;
import com.adaptionsoft.games.uglytrivia.Player;
import com.adaptionsoft.games.uglytrivia.PlayerAdded;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;
    private List<Player> players = new ArrayList<>();

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame;
    }

    @Override
    public Game getGame() {
         aGame = new Game(this.players, aGame);
         return aGame;
    }

    @Override
    public void save(PlayerAdded playerAddedEvent) {
        // insert the player into players table
        players.add(new Player(playerAddedEvent.playerName));
    }

    @Override
    public void save(GoldCoinWon goldCoinWonEvent) {
        Player player = players.stream()
                .filter(p -> p.getName() == goldCoinWonEvent.name)
                .findFirst().get();
        player.goldCoins = goldCoinWonEvent.goldCoinsTotal;
    }
}
