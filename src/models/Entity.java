package models;

public class Entity {
    private boolean isMovable;
    private boolean isPlaySpace;
    private String type;
    Entity(boolean isMovable, boolean isPlaySpace,String type){
        this.isMovable=isMovable;
        this.isPlaySpace=isPlaySpace;
        this.type=type;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public boolean isPlaySpace() {
        return isPlaySpace;
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "isMovable=" + isMovable +
                ", isPlaySpace=" + isPlaySpace +
                ", type='" + type + '\'' +
                '}';
    }
}
