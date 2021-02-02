
package com.adaptionsoft.games.trivia.runner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.adaptionsoft.games.trivia.PlayerWon;
import com.adaptionsoft.games.uglytrivia.*;


public class GameRunner {

	public static void main(String[] args) {
		playGame(new Random());
	}

    private static Deck createDeck() {
        return new Deck();
    }

    public static void playGame(Random rand) {
		EventPublisher eventPublisher = new EventPublisher();

		Game aGame = new Game(createDeck(), new ArrayList<>(), 0);
		GameRepository gameRepository = new InMemoryGameRepository(aGame);
        eventPublisher.registerHandler(PlayerAdded.class, gameRepository::save);
        eventPublisher.registerHandler(GoldCoinWon.class, gameRepository::save);
        eventPublisher.registerHandler(PlayerMoved.class, gameRepository::save);
        eventPublisher.registerHandler(PlayerSentToPenaltyBox.class, gameRepository::save);
        eventPublisher.registerHandler(CurrentPlayerChanged.class, gameRepository::save);

		setupGame(eventPublisher, gameRepository);
// ici on pourrait appeler gamerepository
		List<Object> events;
        do {
			events = playTurn(rand, eventPublisher, gameRepository);
        } while (events.stream().noneMatch(event -> event instanceof PlayerWon));

	}

    private static void setupGame(EventPublisher eventPublisher, GameRepository gameRepository) {
        eventPublisher.applyEvents(gameRepository.getGame().add("Chet"));
        eventPublisher.applyEvents(gameRepository.getGame().add("Pat"));
        eventPublisher.applyEvents(gameRepository.getGame().add("Sue"));
    }

    private static List<Object> playTurn(Random rand, EventPublisher eventPublisher, GameRepository gameRepository) {
        Game aGame = gameRepository.getGame();
        List<Object> events = new ArrayList<>();
        events.addAll(aGame.roll(rand.nextInt(5) + 1));

        if (rand.nextInt(9) == 7) {
            events.addAll(aGame.wrongAnswer());
        } else {
            events.addAll(aGame.wasCorrectlyAnswered());
        }
        eventPublisher.applyEvents(events);
        return events;
    }
}
