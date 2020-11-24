package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class PlayerMoved {
    public final int newLocation;
    public final String name;

    public PlayerMoved(int newLocation, String name) {
        this.newLocation = newLocation;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerMoved that = (PlayerMoved) o;
        return newLocation == that.newLocation &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newLocation, name);
    }

    @Override
    public String toString() {
        return "PlayerMoved{" +
                "newLocation=" + newLocation +
                ", name='" + name + '\'' +
                '}';
    }
}
