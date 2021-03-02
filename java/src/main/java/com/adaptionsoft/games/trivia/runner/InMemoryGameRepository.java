package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryGameRepository implements GameRepository {
    private Game aGame;
    // private List<PlayerDTO> playerDTO = new ArrayList<>();
    // private GameDTO gameDTO = new GameDTO();
    private List<Object> events = new ArrayList<>();

    public InMemoryGameRepository(Game aGame) {
        this.aGame = aGame;
    }

    @Override
    public Game getGame() {
         // create Players from PlayerDTO

         List<PlayerDTO> playerDTOs = new ArrayList<>();
         GameDTO gameDTO = new GameDTO();
         for (Object event: events) {
             playerDTOs = evolve(playerDTOs, gameDTO, event);
         }
        List<Player> players = playerDTOs.stream()
                .map(pd -> new Player(pd.playerName, pd.goldCoins, pd.place, pd.isInPenaltyBox))
                .collect(Collectors.toList());
         aGame = new Game(players, aGame, gameDTO.currentPlayer);
         return aGame;
    }

    private List<PlayerDTO> evolve(List<PlayerDTO> playerDTOs, GameDTO gameDTO, Object event) {
        if (event instanceof PlayerAdded) {
            playerDTOs.add(new PlayerDTO(((PlayerAdded) event).playerName));
        } else if (event instanceof GoldCoinWon) {
            GoldCoinWon goldCoinWonEvent = (GoldCoinWon) event;
            PlayerDTO playerDTO = playerDTOs.stream()
                .filter(p -> p.playerName == goldCoinWonEvent.name)
                .findFirst().get();
            playerDTO.goldCoins = goldCoinWonEvent.goldCoinsTotal;
        }else if (event instanceof PlayerMoved) {
            PlayerMoved playerMovedEvent = (PlayerMoved) event;
            PlayerDTO playerDTO = playerDTOs.stream()
                    .filter(p -> p.playerName == playerMovedEvent.name)
                    .findFirst().get();
            playerDTO.place = playerMovedEvent.newPlace;
        } else if (event instanceof PlayerSentToPenaltyBox) {
            PlayerSentToPenaltyBox playerSentToPenaltyBoxEvent = (PlayerSentToPenaltyBox) event;
            PlayerDTO playerDTO = playerDTOs.stream()
                    .filter(p -> p.playerName == playerSentToPenaltyBoxEvent.name)
                    .findFirst().get();
            playerDTO.isInPenaltyBox = true;
        } else if (event instanceof CurrentPlayerChanged) {
            gameDTO.currentPlayer = ((CurrentPlayerChanged) event).currentPlayer;
        }
        return playerDTOs;
    }

    @Override
    public void save(PlayerAdded playerAddedEvent) {
        events.add(playerAddedEvent);
    }

    @Override
    public void save(GoldCoinWon goldCoinWonEvent) {
        events.add(goldCoinWonEvent);
    }

    @Override
    public void save(PlayerMoved playerMovedEvent) {
        events.add(playerMovedEvent);
    }

    @Override
    public void save(PlayerSentToPenaltyBox playerSentToPenaltyBoxEvent) {
        events.add(playerSentToPenaltyBoxEvent);
    }

    @Override
    public void save(CurrentPlayerChanged currentPlayerChanged) {
        events.add(currentPlayerChanged);
    }
}
