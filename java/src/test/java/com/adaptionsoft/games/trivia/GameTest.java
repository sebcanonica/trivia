package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Deck;
import com.adaptionsoft.games.uglytrivia.EventPublisher;
import com.adaptionsoft.games.uglytrivia.Game;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void itsLockedDown() throws Exception {
        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1,15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());
    }

    @Test
    public void playerWonEventRaisedWhenGameEnded() {
        Game aGame = new Game(new Deck());
        aGame.add("toto");
        List<Object> events = null;
        for (int i =0; i<6;i++){
            aGame.roll(1);
            events = aGame.wasCorrectlyAnswered();
        }
        assertTrue(events.contains(new PlayerWon("toto")));
    }
}
