package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.*;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void itsLockedDown() throws Exception {
        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1, 15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());
    }

    @Test
    public void playerWonEventRaisedWhenGameEnded() {
        List<Player> players = Arrays.asList(
                new Player("toto", 5, 0, false)
        );
        Game aGame = new Game(new Deck(), players, 0);
        aGame.roll(1);
        List<Object> events = aGame.wasCorrectlyAnswered();
        assertTrue(events.contains(new PlayerWon("toto")));
    }

    @Test
    public void diceRolled_playerMoved_questionAsked_events_raised_when_game_roll() {
        List<Player> players = Arrays.asList(
                new Player("toto")
        );
        Game aGame = new Game(new Deck(), players, 0);
        List<Object> events = aGame.roll(1);
        assertThat(events).containsExactly(
                new DiceRolled("toto", 1),
                new PlayerMoved(1, "toto"),
                new QuestionAsked("Science", "Science Question 0")
        );
    }

    @Test
    public void diceRolled_playerMoved_questionAsked_events_raised_when_game_roll_on_second_player() {
        List<Player> players = Arrays.asList(
                new Player("toto"),
                new Player("tata")
        );
        Game aGame = new Game(new Deck(), players, 1);
        List<Object> events = aGame.roll(1);
        assertThat(events).containsExactly(
                new DiceRolled("tata", 1),
                new PlayerMoved(1, "tata"),
                new QuestionAsked("Science", "Science Question 0")
        );
    }

    @Test
    public void player_should_have_place_injected() {
        List<Player> players = Arrays.asList(
                new Player("toto", 0, 1, false)
        );
        Game aGame = new Game(new Deck(), players, 0);
        List<Object> events = aGame.roll(1);
        assertThat(events).containsExactly(
                new DiceRolled("toto", 1),
                new PlayerMoved(2, "toto"),
                new QuestionAsked("Sports", "Sports Question 0")
        );
    }

    @Test
    public void player_should_not_move_when_in_penalty_box_and_rolling_an_even_number() {
        List<Player> players = Arrays.asList(
                new Player("toto", 0, 1, true)
        );
        Game aGame = new Game(new Deck(), players, 0);

        List<Object> events = aGame.roll(2);

        assertThat(events).containsExactly(
                new DiceRolled("toto", 2),
                new NotGettingOutOfPenaltyBox("toto"));
    }
}
