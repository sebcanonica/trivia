package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Deck {
    LinkedList<String> popQuestions = new LinkedList();
    LinkedList<String> scienceQuestions = new LinkedList();
    LinkedList<String> sportsQuestions = new LinkedList();
    LinkedList<String> rockQuestions = new LinkedList();

    public Deck() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public String drawQuestionFor(String currentCategory) {
        if (currentCategory == "Pop")
            return popQuestions.removeFirst();
        if (currentCategory == "Science")
            return scienceQuestions.removeFirst();
        if (currentCategory == "Sports")
            return sportsQuestions.removeFirst();
        //if (currentCategory == "Rock")
        return rockQuestions.removeFirst();

    }
}
