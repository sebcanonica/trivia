
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Deck;
import com.adaptionsoft.games.uglytrivia.EventPublisher;
import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		playGame(new Random());
	}

    private static Deck createDeck() {
        return new Deck();
    }

    public static void playGame(Random rand) {
		EventPublisher eventPublisher = new EventPublisher();
		Game aGame = new Game(createDeck(), eventPublisher);

		eventPublisher.applyEvents(aGame.add("Chet"));
		eventPublisher.applyEvents(aGame.add("Pat"));
		eventPublisher.applyEvents(aGame.add("Sue"));

		do {

			eventPublisher.applyEvents(aGame.roll(rand.nextInt(5) + 1));

			if (rand.nextInt(9) == 7) {
				notAWinner = true;
				eventPublisher.applyEvents(aGame.wrongAnswer());
			} else {
				Game.EventsAndNotAWinner eventsAndNotAWinner = aGame.wasCorrectlyAnswered();
				eventPublisher.applyEvents(eventsAndNotAWinner.getEvents());
				notAWinner = eventsAndNotAWinner.isNotAWinner();
			}



		} while (notAWinner);
	}
}
