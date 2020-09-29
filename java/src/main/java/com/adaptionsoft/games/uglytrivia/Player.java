package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int place = 0;
    private int goldCoins = 0;

    public Player(String playerName) {
        name = playerName;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public PlayerMoved move(int roll) {
        this.place = (place + roll) % 12;
        return new PlayerMoved(place, name);
    }

    public GoldCoinWon winGoldCoin() {
        goldCoins++;
        return new GoldCoinWon(name, goldCoins);
    }

    public boolean hasWon() {
        return goldCoins == 6;
    }
}
