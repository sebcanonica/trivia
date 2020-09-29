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
        registerHandler(handlers, PlayerMoved.class, Game::handlePlayerMoved);
        registerHandler(handlers, QuestionAsked.class, Game::handleQuestionAsked);
        registerHandler(handlers, PlayerSentToPenaltyBox.class, Game::handlePlayerSentToPenaltyBox);
        registerHandler(handlers, DiceRolled.class, Game::handle);
        registerHandler(handlers, GetOutOfPenaltyBox.class, Game::handle);
        registerHandler(handlers, NotGettingOutOfPenaltyBox.class, Game::handle);
        registerHandler(handlers, GoldCoinWon.class, Game::handle);
        registerHandler(handlers, PlayerAdded.class, Game::handle);

        for (Object event: events) {
            Consumer handler = handlers.getOrDefault(event.getClass(), o -> { });
            handler.accept(event);
        }
    }

    private <T> void registerHandler(HashMap<Class, Consumer> handlers, Class<T> clazz, Consumer<T> handler) {
        handlers.put(clazz, handler);
    }

    private static void handle(PlayerAdded event) {
        System.out.println(event.playerName + " was added");
        System.out.println("They are player number " + event.totalNumberOfPlayers);
    }

    private static void handle(GoldCoinWon goldCoinWon) {
        System.out.println(goldCoinWon.name
                + " now has "
                + goldCoinWon.goldCoinsTotal
                + " Gold Coins.");
    }

    private static void handle(NotGettingOutOfPenaltyBox event) {
        System.out.println(event.name + " is not getting out of the penalty box");
    }

    private static void handle(GetOutOfPenaltyBox event) {
        System.out.println(event.name + " is getting out of the penalty box");
    }

    private static void handle(DiceRolled event) {
        System.out.println(event.name + " is the current player");
        System.out.println("They have rolled a " + event.roll);
    }

    private static void handlePlayerSentToPenaltyBox(PlayerSentToPenaltyBox playerSentToPenaltyBox) {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerSentToPenaltyBox.name + " was sent to the penalty box");
    }

    private static void handlePlayerMoved(PlayerMoved playerMoved) {
        System.out.println(playerMoved.name
                + "'s new location is "
                + playerMoved.newLocation);
        System.out.println("The category is " + currentCategory(playerMoved.newLocation));
    }

    private static void handleQuestionAsked(QuestionAsked questionAsked) {
        System.out.println(questionAsked.question);
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
                System.out.println("Answer was correct!!!!");
                events.add(players.get(currentPlayer).winGoldCoin());

                notAWinner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players
                        .size()) currentPlayer = 0;
            } else {
                currentPlayer++;
                if (currentPlayer == players
                        .size()) currentPlayer = 0;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            events.add(players.get(currentPlayer).winGoldCoin());

            notAWinner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players
                    .size()) currentPlayer = 0;
        }

        applyEvents(events);
        return notAWinner;
    }

    public boolean wrongAnswer() {
        PlayerSentToPenaltyBox playerSentToPenaltyBox = players.get(currentPlayer).goToPenaltyBox();
        currentPlayer++;
        if (currentPlayer == players
                .size()) currentPlayer = 0;

        applyEvents(Arrays.asList(playerSentToPenaltyBox));
        return true;
    }


    private boolean didPlayerWin() {
        return !players.get(currentPlayer).hasWon();
    }
}
