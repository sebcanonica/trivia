
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Deck;
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
		Game aGame = new Game(createDeck());

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		do {

			aGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
