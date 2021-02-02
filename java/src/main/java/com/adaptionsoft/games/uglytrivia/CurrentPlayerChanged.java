package com.adaptionsoft.games.uglytrivia;

public class CurrentPlayerChanged {
    public int currentPlayer;

    public CurrentPlayerChanged(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentPlayerChanged that = (CurrentPlayerChanged) o;

        return currentPlayer == that.currentPlayer;
    }

    @Override
    public int hashCode() {
        return currentPlayer;
    }
}
