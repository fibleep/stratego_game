package models;

import java.util.List;
import java.util.Objects;

public class Space {

    private int row;
    private int column;
    private Entity entity;

    public Space(int row, int column, Entity entity) {
        this.row = row;
        this.column = column;
        this.entity = entity;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Pawn getPawn() {
        boolean isPiece = entity.isPlaySpace() && !Objects.equals(entity.getType(), "Empty");
        if (isPiece) {
            return (Pawn) entity;
        }
        return null;
    }

    @Override
    public String toString() {
        return entity + " on row " + row + " on column " + column;
    }
}
