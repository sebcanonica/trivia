package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.PlayerAdded;

public interface GameRepository {
    Game getGame();

    void save(PlayerAdded playerAddedEvent);
}
