package com.adaptionsoft.games.uglytrivia;

import java.util.Objects;

public class GetOutOfPenaltyBox {
    public final String name;

    public GetOutOfPenaltyBox(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetOutOfPenaltyBox that = (GetOutOfPenaltyBox) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
