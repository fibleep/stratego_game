package models;

import Database.Database;

import java.util.*;

public class Logger {
   // private String turnLog;
   Map<String,Integer> turnLog = new HashMap<String,Integer>();
    private List<Coords> xyLog;
    private String name;
    private boolean isPlayer;
    private Database SGDB;
    private int turnScore;
    private int index = 0;

    public Logger(String name,boolean isPlayer){
        this.name=name;
        this.isPlayer=isPlayer;
        xyLog=new ArrayList<>();

    }
    public void log(Space start,Space target){
        StringBuilder currentTurnLog=new StringBuilder();
        Coords currCoords = new Coords(start,target);
        StringBuilder result = new StringBuilder();
        result.append(name).append(" has moved their pawn ").append(" from position x:").
                append(9-start.getColumn()).append(" y: ").append(start.getRow()).append(" to position x: ").
                append(9-target.getColumn()).append(" y: ").append(target.getRow());

        currentTurnLog.append(formulateTurnReport(this.name,start,target));
        turnLog.put( result.toString(),index);
       // turnLog = turnLog + "\n" + currentTurnLog;
        xyLog.add(currCoords);
        if(Objects.equals(target.getEntity().getType(), "Flag")){
            SGDB.dbSaveGame(this.isPlayer);

        }else{
            SGDB.dbSaveTurn(currentTurnLog,this.isPlayer,this.turnScore);
        }
    }

    public String formulateTurnReport(String name, Space start, Space target){
        StringBuilder result = new StringBuilder();
        result.append(name).append(" has moved their ").append(start.getPawn().getType()).append(" from position x:").append(9-start.getColumn()).append(" y: ").append(start.getRow()).append(" to position x: ").append(9-target.getColumn()).append(" y: ").append(target.getRow());
        if(Objects.equals(target.getEntity().getType(), "Empty")) this.turnScore=2;
        else if(target.getPawn()!=null&&!target.getEntity().getType().equals("Empty")){
            switch(target.getPawn().getRank()) {
                case 0 -> {
                    if(Objects.equals(start.getPawn().getType(), "Miner")) {
                        result.append(" and defused a Bomb");
                        this.turnScore=15;
                    }
                    else{
                        target.getPawn().setKnown(true);
                    }
                }
                case 11 ->{
                    result.append(" and captured the Flag");
                    this.turnScore=200;
                }
                default->{
                    int targetRank = target.getPawn().getRank();
                    int startRank = start.getPawn().getRank();
                    if(startRank>targetRank) {
                        this.turnScore=10;
                        result.append(" and killed a ").append(target.getPawn().getType());
                        start.getPawn().setKnown(true);
                    }
                    if(startRank==targetRank){
                        this.turnScore=5;
                        result.append(" and traded");
                    }
                    if(startRank<targetRank){
                        this.turnScore=0;
                        result.append(" and died");
                        target.getPawn().setKnown(true);
                    }

                }
            }
        }
        return result.toString();
    }


    public boolean isPermitted(Space start,Space toMove){
        boolean isPermitted =true;
        if(start==null||toMove==null) return false;
        if(xyLog.size()>1) {
            Coords twoMovesAgo = xyLog.get(xyLog.size()-2);
            isPermitted=!twoMovesAgo.equals(new Coords(start,toMove));

        }
        return isPermitted;
    }

    public Map<String, Integer> getTurnLog() {
        return turnLog;
    }

    public void setAccessPoint(Database SGDB) {
        this.SGDB = SGDB;
    }
}
