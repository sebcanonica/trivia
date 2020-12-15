package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class GoldCoinWon {
    public final String name;
    public final int goldCoinsTotal;

    public GoldCoinWon(String name, int goldCoinsTotal) {
        this.name = name;
        this.goldCoinsTotal = goldCoinsTotal;
    }

    @Override
    public String toString() {
        return "GoldCoinWon{" +
                "name='" + name + '\'' +
                ", goldCoinsTotal=" + goldCoinsTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoldCoinWon that = (GoldCoinWon) o;
        return goldCoinsTotal == that.goldCoinsTotal &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, goldCoinsTotal);
    }
}
