
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
		List<Object> events = new ArrayList<>();
		EventPublisher eventPublisher = new EventPublisher();
		Game aGame = new Game(createDeck(), new ArrayList<>(), 0, false);

		events.addAll(aGame.add("Chet"));
		events.addAll(aGame.add("Pat"));
		events.addAll(aGame.add("Sue"));

		do {
			events.addAll(aGame.roll(rand.nextInt(5) + 1));

			if (rand.nextInt(9) == 7) {
				events.addAll(aGame.wrongAnswer());
			} else {
				events.addAll(aGame.wasCorrectlyAnswered());
			}
		} while (events.stream().noneMatch(event -> event instanceof PlayerWon));

		eventPublisher.applyEvents(events);
	}
}
