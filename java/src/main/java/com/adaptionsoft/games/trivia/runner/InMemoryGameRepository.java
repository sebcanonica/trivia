package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;
    private List<PlayerDTO> playerDTO = new ArrayList<>();
    private GameDTO gameDTO = new GameDTO();

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame;
    }

    @Override
    public Game getGame() {
         // create Players from PlayerDTO
         List<Player> players = this.playerDTO.stream()
                 .map(pd -> new Player(pd.playerName, pd.goldCoins, pd.place, pd.isInPenaltyBox))
                 .collect(Collectors.toList());
         aGame = new Game(players, aGame, gameDTO.currentPlayer);
         return aGame;
    }

    @Override
    public void save(PlayerAdded playerAddedEvent) {
        playerDTO.add(new PlayerDTO(playerAddedEvent.playerName));
    }

    @Override
    public void save(GoldCoinWon goldCoinWonEvent) {
        PlayerDTO playerDTO = this.playerDTO.stream()
                .filter(p -> p.playerName == goldCoinWonEvent.name)
                .findFirst().get();
        playerDTO.goldCoins = goldCoinWonEvent.goldCoinsTotal;
    }

    @Override
    public void save(PlayerMoved playerMovedEvent) {
        PlayerDTO playerDTO = this.playerDTO.stream()
                .filter(p -> p.playerName == playerMovedEvent.name)
                .findFirst().get();
        playerDTO.place = playerMovedEvent.newPlace;
    }

    @Override
    public void save(PlayerSentToPenaltyBox playerSentToPenaltyBoxEvent) {
        PlayerDTO playerDTO = this.playerDTO.stream()
                .filter(p -> p.playerName == playerSentToPenaltyBoxEvent.name)
                .findFirst().get();
        playerDTO.isInPenaltyBox = true;
    }

    @Override
    public void save(CurrentPlayerChanged currentPlayerChanged) {
        gameDTO.currentPlayer = currentPlayerChanged.currentPlayer;
    }
}
