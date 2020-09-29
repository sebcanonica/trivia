package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
	// replace by access to Players
    boolean[] inPenaltyBox = new boolean[6];
    List<Player> players = new ArrayList<>();
	private final Deck deck;

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(Deck deck) {
        this.deck = deck;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {


        players.add(new Player(playerName));
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer).getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");
                movePlayer(roll);
            } else {
                System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            movePlayer(roll);
        }

    }

    private void movePlayer(int roll) {
        Player player = players.get(currentPlayer);
        player.move(roll);


        System.out.println(players.get(currentPlayer).getName()
                + "'s new location is "
                + player.getPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
		String question = deck.drawQuestionFor(currentCategory());
		System.out.println(question);
    }


    private String currentCategory() {
        Player player = players.get(currentPlayer);
        if (player.getPlace() == 0) return "Pop";
        if (player.getPlace() == 4) return "Pop";
        if (player.getPlace() == 8) return "Pop";
        if (player.getPlace() == 1) return "Science";
        if (player.getPlace() == 5) return "Science";
        if (player.getPlace() == 9) return "Science";
        if (player.getPlace() == 2) return "Sports";
        if (player.getPlace() == 6) return "Sports";
        if (player.getPlace() == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                players.get(currentPlayer).winGoldCoin();

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players
                        .size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players
                        .size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            players.get(currentPlayer).winGoldCoin();

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players
                    .size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players
                .size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !players.get(currentPlayer).hasWon();
    }
}
