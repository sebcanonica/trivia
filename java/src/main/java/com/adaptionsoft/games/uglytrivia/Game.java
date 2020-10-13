package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private final IPublishEvent eventPublisher;
    List<Player> players = new ArrayList<>();
	private final Deck deck;

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(Deck deck, IPublishEvent eventPublisher) {
        this.deck = deck;
        this.eventPublisher = eventPublisher;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        eventPublisher.applyEvents(Arrays.asList(new PlayerAdded(playerName, players.size())));
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        List<Object> events = new ArrayList<>();
        events.add(new DiceRolled(players.get(currentPlayer).getName(), roll));

        if (players.get(currentPlayer).isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                events.add(new GetOutOfPenaltyBox(players.get(currentPlayer).getName()));
                events.addAll(movePlayer(roll));
            } else {
                events.add(new NotGettingOutOfPenaltyBox(players.get(currentPlayer).getName()));
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            events.addAll(movePlayer(roll));
        }

        eventPublisher.applyEvents(events);
    }

    private List<Object> movePlayer(int roll) {
        Player player = players.get(currentPlayer);
        PlayerMoved playerMoved = player.move(roll);
        QuestionAsked questionAsked = deck.drawQuestionFor(currentCategory(playerMoved.newLocation));
        return Arrays.asList(playerMoved, questionAsked);
    }

    private static String currentCategory(int location) {
        if (location % 4 == 0) return "Pop";
        if (location % 4 == 1) return "Science";
        if (location % 4 == 2) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        List<Object> events = new ArrayList<>();
        boolean notAWinner = true;
        if (players.get(currentPlayer).isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                events.add(players.get(currentPlayer).winGoldCoin());

                notAWinner = didPlayerWin();
            }
        } else {
            events.add(players.get(currentPlayer).winGoldCoin());

            notAWinner = didPlayerWin();
        }

        nextPlayer();
        eventPublisher.applyEvents(events);
        return notAWinner;
    }

    public boolean wrongAnswer() {
        PlayerSentToPenaltyBox playerSentToPenaltyBox = players.get(currentPlayer).goToPenaltyBox();
        nextPlayer();

        eventPublisher.applyEvents(Arrays.asList(playerSentToPenaltyBox));
        return true;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players
                .size()) currentPlayer = 0;
    }

    private boolean didPlayerWin() {
        return !players.get(currentPlayer).hasWon();
    }
}
