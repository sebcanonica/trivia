package com.adaptionsoft.games.trivia;

public class PlayerWon {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerWon playerWon = (PlayerWon) o;

        return name != null ? name.equals(playerWon.name) : playerWon.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public PlayerWon(String name) {
        this.name = name;
    }

}
