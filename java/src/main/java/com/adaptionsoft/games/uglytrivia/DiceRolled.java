package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class DiceRolled {
    public final String name;
    public final int roll;

    public DiceRolled(String name, int roll) {
        this.name = name;
        this.roll = roll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiceRolled that = (DiceRolled) o;
        return roll == that.roll &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, roll);
    }

    @Override
    public String toString() {
        return "DiceRolled{" +
                "name='" + name + '\'' +
                ", roll=" + roll +
                '}';
    }
}
