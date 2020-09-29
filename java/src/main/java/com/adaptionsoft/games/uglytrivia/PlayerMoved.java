package com.adaptionsoft.games.uglytrivia;public class PlayerMoved {
    public final int newLocation;
    public final String name;

    public PlayerMoved(int newLocation, String name) {
        this.newLocation = newLocation;
        this.name = name;
    }
}