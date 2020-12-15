package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class NotGettingOutOfPenaltyBox {
    public final String name;

    public NotGettingOutOfPenaltyBox(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotGettingOutOfPenaltyBox that = (NotGettingOutOfPenaltyBox) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
