package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class QuestionAsked {
    public final String question;
    public String category;

    public QuestionAsked(String category, String question) {
        this.category = category;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionAsked that = (QuestionAsked) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, category);
    }

    @Override
    public String toString() {
        return "QuestionAsked{" +
                "question='" + question + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
