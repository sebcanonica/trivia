package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Game {
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

        applyEvents(Arrays.asList(new PlayerAdded(playerName, players.size())));
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

        applyEvents(events);
    }

    private List<Object> movePlayer(int roll) {
        Player player = players.get(currentPlayer);
        PlayerMoved playerMoved = player.move(roll);
        QuestionAsked questionAsked = deck.drawQuestionFor(currentCategory(playerMoved.newLocation));
        return Arrays.asList(playerMoved, questionAsked);
    }

    private void applyEvents(List<Object> events) {
        HashMap<Class, Consumer> handlers = new HashMap<>();
        registerHandler(handlers, PlayerMoved.class, GameHandler::handlePlayerMoved);
        registerHandler(handlers, QuestionAsked.class, GameHandler::handleQuestionAsked);
        registerHandler(handlers, PlayerSentToPenaltyBox.class, GameHandler::handlePlayerSentToPenaltyBox);
        registerHandler(handlers, DiceRolled.class, GameHandler::handle);
        registerHandler(handlers, GetOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(handlers, NotGettingOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(handlers, GoldCoinWon.class, GameHandler::handle);
        registerHandler(handlers, PlayerAdded.class, GameHandler::handle);

        for (Object event: events) {
            Consumer handler = handlers.getOrDefault(event.getClass(), o -> { });
            handler.accept(event);
        }
    }

    private <T> void registerHandler(HashMap<Class, Consumer> handlers, Class<T> clazz, Consumer<T> handler) {
        handlers.put(clazz, handler);
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
        applyEvents(events);
        return notAWinner;
    }

    public boolean wrongAnswer() {
        PlayerSentToPenaltyBox playerSentToPenaltyBox = players.get(currentPlayer).goToPenaltyBox();
        nextPlayer();

        applyEvents(Arrays.asList(playerSentToPenaltyBox));
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
