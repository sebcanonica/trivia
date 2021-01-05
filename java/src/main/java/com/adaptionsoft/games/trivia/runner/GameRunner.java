
package com.adaptionsoft.games.trivia.runner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.adaptionsoft.games.trivia.PlayerWon;
import com.adaptionsoft.games.uglytrivia.Deck;
import com.adaptionsoft.games.uglytrivia.EventPublisher;
import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	public static void main(String[] args) {
		playGame(new Random());
	}

    private static Deck createDeck() {
        return new Deck();
    }

    public static void playGame(Random rand) {
		EventPublisher eventPublisher = new EventPublisher();

		Game aGame = new Game(createDeck(), new ArrayList<>(), 0, false);
		GameRepository gameRepository = new InMemoryGameRepository(aGame);
        setupGame(eventPublisher, gameRepository);

		List<Object> events;
        do {
			events = playTurn(rand, eventPublisher, gameRepository);
        } while (events.stream().noneMatch(event -> event instanceof PlayerWon));

	}

    private static void setupGame(EventPublisher eventPublisher, GameRepository gameRepository) {
		Game aGame = gameRepository.getGame();
		List<Object> events = new ArrayList<>();
        events.addAll(aGame.add("Chet"));
        events.addAll(aGame.add("Pat"));
        events.addAll(aGame.add("Sue"));
        eventPublisher.applyEvents(events);
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
