package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int place = 0;

    public Player(String playerName) {
        name = playerName;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void move(int roll) {
        this.place = (place + roll) % 12;
    }
}
