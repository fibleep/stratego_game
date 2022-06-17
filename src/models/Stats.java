package models;

import java.util.*;

public class Stats {

    List<Long> turnTimes = new ArrayList<>();
    Map<String,Integer> mapMoves = new HashMap<String,Integer>();
    int totalPoints;
    long totalTime;
    String name;
    int gameID;
    public Stats(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public List<Long> getTurnTimes() {
        return turnTimes;
    }

    public void setTurnTimes(List<Long> turnTimes) {
        this.turnTimes = turnTimes;
    }

    public Map<String, Integer> getMapMoves() {
        return mapMoves;
    }

    public void setMapMoves(Map<String, Integer> mapMoves) {
        this.mapMoves = mapMoves;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "turnTimes=" + turnTimes +
                ", mapMoves=" + mapMoves +
                ", totalPoints=" + totalPoints +
                ", totalTime=" + totalTime +
                ", name='" + name + '\'' +
                ", gameID=" + gameID +
                '}';
    }
}
