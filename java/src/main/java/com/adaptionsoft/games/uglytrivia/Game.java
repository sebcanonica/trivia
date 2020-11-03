package com.adaptionsoft.games.uglytrivia;

/** TODO
 *  - finir refacto NotAWinner
 *  - notion d'aggrégat (1 pour Game)
 *  - chargement d'état du Game pour tests unitaires plus simples
 *    (séparation calcul des évenements et changement d'état)
 */

import com.adaptionsoft.games.trivia.PlayerWon;

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

    public List<Object> add(String playerName) {
        players.add(new Player(playerName));

        return Arrays.asList(new PlayerAdded(playerName, players.size()));
    }

    public int howManyPlayers() {
        return players.size();
    }

    public List<Object> roll(int roll) {
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

        return events;
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

    public EventsAndNotAWinner wasCorrectlyAnswered() {
        List<Object> events = new ArrayList<>();
        if (players.get(currentPlayer).isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                events.add(players.get(currentPlayer).winGoldCoin());
            }
        } else {
            events.add(players.get(currentPlayer).winGoldCoin());
        }
        boolean notAWinner = didPlayerWin();
        if (!notAWinner) {
            events.add(new PlayerWon(players.get(currentPlayer).getName()));
        }

        nextPlayer();
        return new EventsAndNotAWinner(events,notAWinner);
    }

    public List<Object> wrongAnswer() {
        PlayerSentToPenaltyBox playerSentToPenaltyBox = players.get(currentPlayer).goToPenaltyBox();
        nextPlayer();

        return Arrays.asList(playerSentToPenaltyBox);
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players
                .size()) currentPlayer = 0;
    }

    private boolean didPlayerWin() {
        return !players.get(currentPlayer).hasWon();
    }

    public class EventsAndNotAWinner {
        private List<Object> events;
        private boolean notAWinner;

        public EventsAndNotAWinner(List<Object> events, boolean notAWinner) {
            this.events = events;
            this.notAWinner = notAWinner;
        }

        public boolean isNotAWinner() {
            return notAWinner;
        }

        public List<Object> getEvents() {
            return events;
        }
    }
}
