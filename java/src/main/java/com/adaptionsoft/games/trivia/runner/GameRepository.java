package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.*;

public interface GameRepository {
    Game getGame();

    void save(PlayerAdded playerAddedEvent);
    void save(GoldCoinWon goldCoinWonEvent);

    void save(PlayerMoved playerMovedEvent);

    void save(PlayerSentToPenaltyBox playerSentToPenaltyBoxEvent);

    void save(CurrentPlayerChanged currentPlayerChanged);
}
