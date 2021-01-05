package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame; 
    }

    @Override
    public Game getGame() {
        return aGame;
    }
}
