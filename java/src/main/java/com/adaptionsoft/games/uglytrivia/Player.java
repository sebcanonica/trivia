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

    public void move(int roll) {
        this.place = (place + roll) % 12;
    }

    public GoldCoinWon winGoldCoin() {
        goldCoins++;
        System.out.println(name
                + " now has "
                + goldCoins
                + " Gold Coins.");
    }

    public boolean hasWon() {
        return goldCoins == 6;
    }
}
