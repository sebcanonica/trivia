package com.adaptionsoft.games.uglytrivia;

/** TODO
 *  - notion d'aggrégat (1 pour Game)
 *  - chargement d'état du Game pour tests unitaires plus simples
 *    (séparation calcul des évenements et changement d'état)
 *  - passer Game en immutable
 *
 *  - Poure 24/11
 *  - Ajouter notion d'aggregats (non event sourcé)
 *  - Voir comment initialiser le Game avec un état donné
 */

import com.adaptionsoft.games.trivia.PlayerWon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Object> wasCorrectlyAnswered() {
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
        return events;
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

}
