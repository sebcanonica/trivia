package com.adaptionsoft.games.uglytrivia;

public class QuestionAsked {
    public final String question;
    public String category;

    public QuestionAsked(String category, String question) {
        this.category = category;
        this.question = question;
    }
}
