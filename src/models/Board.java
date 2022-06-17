package models;

import java.util.Random;

public class Board {
    private Space[][] spaces;
    private Player player;
    private AI AI;


    public Board(Player player, AI AI) {
        this.player = player;
        this.AI = AI;
        this.spaces = new Space[10][10];
        initializeBoard();
    }


    public void initializeBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                spaces[i][j] = new Space(i, j,new Empty());
            }
        }
    }

    /**
     * Randomizes the board based on different presets
     *
     * @param pawns   array of pawns
     * @param boardNr preset number
     * @return a randomized board
     */
    public int[] randomizeBoard() {
        Random rand = new Random();
      return switch (rand.nextInt(1, 7)) {
            case 1 -> new int[]{3, 3, 0, 11, 0, 3, 2, 0, 3, 2, 4, 6, 5, 0, 5, 8, 7, 4, 0, 4, 2, 8, 7, 7, 2, 9, 1, 0, 4, 5, 6, 2, 10, 3, 6, 2, 5, 2, 2, 6};
            case 2 -> new int[]{3, 0, 0, 11, 0, 4, 3, 3, 3, 7, 2, 5, 4, 0, 5, 6, 1, 7, 2, 7, 5, 0, 0, 4, 2, 9, 8, 8, 2, 4, 6, 2, 2, 2, 6, 2, 3, 5, 6, 10};
            case 3 -> new int[]{11, 0, 3, 3, 2, 4, 0, 4, 0, 3, 0, 5, 5, 6, 2, 0, 3, 1, 8, 4, 3, 8, 7, 7, 2, 0, 5, 7, 2, 5, 6, 2, 10, 2, 2, 6, 9, 4, 2, 6};
            case 4 -> new int[]{2, 3, 0, 11, 0, 4, 3, 3, 2, 3, 4, 0, 4, 0, 5, 7, 1, 7, 4, 6, 5, 0, 5, 0, 6, 2, 8, 7, 2, 10, 6, 2, 3, 9, 2, 6, 2, 5, 8, 2};
            case 5 -> new int[]{0, 11, 0, 3, 3, 4, 0, 3, 2, 4, 4, 0, 5, 6, 2, 5, 7, 1, 6, 0, 8, 5, 7, 10, 5, 0, 7, 8, 2, 3, 2, 6, 3, 2, 2, 2, 4, 2, 6, 9};
            case 6 -> new int[]{11, 0, 3, 3, 0, 3, 2, 4, 0, 3, 0, 5, 6, 5, 3, 7, 2, 0, 4, 5, 4, 8, 7, 2, 4, 8, 1, 0, 2, 7, 2, 6, 10, 2, 6, 2, 9, 5, 6, 2};
            default -> new int[]{1};
        };
    }

    /**
     * builds a board based on data given
     *
     * @param ranks ranks of pawns
     * @param pawns board to be set
     * @param playerSetup
     * @return a built board
     */
    public void buildBoard(int[] playerSetup, int[] enemySetup) {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                spaces[i][j].setEntity(new Pawn(decodeType(playerSetup[index]), player.getColor()));
                index++;
            }
        }
        index = 0;
        for (int i = 9; i > 5; i--) {
            for (int j = 9; j >= 0; j--) {
                spaces[i][j].setEntity(new Pawn(decodeType(enemySetup[index]), AI.getColor()));
                index++;
            }
        }
        spaces[4][2].setEntity(new Lake());
        spaces[4][3].setEntity(new Lake());
        spaces[4][6].setEntity(new Lake());
        spaces[4][7].setEntity(new Lake());
        spaces[5][2].setEntity(new Lake());
        spaces[5][3].setEntity(new Lake());
        spaces[5][6].setEntity(new Lake());
        spaces[5][7].setEntity(new Lake());
    }

    public String decodeType(int rank) {
        return switch (rank) {
            case 0 -> "Bomb";
            case 1 -> "Spy";
            case 2 -> "Scout";
            case 3 -> "Miner";
            case 4 -> "Sergeant";
            case 5 -> "Lieutenant";
            case 6 -> "Captain";
            case 7 -> "Major";
            case 8 -> "Colonel";
            case 9 -> "General";
            case 10 -> "Marshal";
            case 11 -> "Flag";
            default -> throw new IllegalStateException("Unexpected value: " + rank);
        };
    }
    public int encodeType(String type){
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
    public void setSpace(int y,int x,Entity entity){
        spaces[y][x].setEntity(entity);
    }
    public Space getSpace(int y,int x){
        return spaces[y][x];
    }
    public Space[][] getSpaces() {
        return spaces;
    }


    public Player getPlayer() {
        return player;
    }

    public models.AI getAI() {
        return AI;
    }
}
