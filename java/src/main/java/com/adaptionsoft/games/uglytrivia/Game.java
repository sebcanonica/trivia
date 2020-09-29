package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

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
        PlayerMoved playerMoved = player.move(roll);
        QuestionAsked questionAsked = deck.drawQuestionFor(currentCategory(playerMoved.newLocation));
        List<Object> events = Arrays.asList(playerMoved, questionAsked);

        applyEvents(events);
    }

    private void applyEvents(List<Object> events) {
        HashMap<Class, Consumer> handlers = new HashMap<>();
        handlers.put(PlayerMoved.class, this::handlePlayerMoved);
        handlers.put(QuestionAsked.class, this::handleQuestionAsked);

        for (Object event: events) {
            Consumer handler = handlers.getOrDefault(event.getClass(), o -> { });

            handler.accept(event);
        }
    }

    private void handlePlayerMoved(Object event) {
        PlayerMoved playerMoved = (PlayerMoved) event;
        System.out.println(playerMoved.name
                + "'s new location is "
                + playerMoved.newLocation);
        System.out.println("The category is " + currentCategory(playerMoved.newLocation));
    }

    private void handleQuestionAsked(Object event) {
        QuestionAsked questionAsked = (QuestionAsked) event;

        System.out.println(questionAsked.question);
    }

    private static String currentCategory(int location) {
        if (location % 4 == 0) return "Pop";
        if (location % 4 == 1) return "Science";
        if (location % 4 == 2) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                final GoldCoinWon goldCoinWon = players.get(currentPlayer).winGoldCoin();
                System.out.println(goldCoinWon.name
                        + " now has "
                        + goldCoinWon.goldCoinsTotal
                        + " Gold Coins.");

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
            final GoldCoinWon goldCoinWon = players.get(currentPlayer).winGoldCoin();
            System.out.println(goldCoinWon.name
                    + " now has "
                    + goldCoinWon.goldCoinsTotal
                    + " Gold Coins.");

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
