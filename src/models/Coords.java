package models;

import java.util.Objects;

public class Coords {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Coords(Space space, Space target) {
        this.y1 = space.getRow();
        this.x1 = space.getColumn();
        this.y2 = target.getRow();
        this.x2 = target.getColumn();
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return getX1() == coords.getX1() && getY1() == coords.getY1() && getX2() == coords.getX2() && getY2() == coords.getY2();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX1(), getY1(), getX2(), getY2());
    }
}
