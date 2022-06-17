package models;

public class Pawn extends Entity {

//    private final String type;
    private final String color;
    private boolean isKnown = false;
    private final int rank;


    public Pawn(String type, String color) {
        super(!type.equals("Flag") && !type.equals("Bomb"), true,type);
//        this.type = type;
        this.color = color;
        this.rank=getRank(type);
    }


    private int getRank(String type){
        return switch (type) {
            case "Bomb" ->0;
            case "Spy"->1;
            case "Scout"->2;
            case "Miner"->3;
            case "Sergeant"->4;
            case "Lieutenant"->5;
            case "Captain"->6;
            case "Major"->7;
            case "Colonel"->8;
            case "General"->9;
            case "Marshal"->10;
            case "Flag"->11;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public int getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }
}
