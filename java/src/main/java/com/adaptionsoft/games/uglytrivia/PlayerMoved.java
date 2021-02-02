package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class PlayerMoved {
    public final int newPlace;
    public final String name;

    public PlayerMoved(int newPlace, String name) {
        this.newPlace = newPlace;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerMoved that = (PlayerMoved) o;
        return newPlace == that.newPlace &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPlace, name);
    }

    @Override
    public String toString() {
        return "PlayerMoved{" +
                "newLocation=" + newPlace +
                ", name='" + name + '\'' +
                '}';
    }
}
