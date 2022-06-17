package models;


import java.text.ParseException;
import java.util.*;

public class AI {

    private String color;
    private Space playerFlag;
    private Space playerMarshal;
    private Space playerSpy;
    private List<Space> playerBombs;
    private Logger logger;
    private Board boardCopy;
    private String name;

    public AI(){
        String[] names = {"Alexander The Great","Napoleon","Caesar","Bossi Granda","Genghis Khan","Queen Victoria","Isabella I","Catherine The Great","Jan Sobieski","Oda Nobunaga","Gustavus Adolphus","Maximillian I Habsburg"};
        Random rand = new Random();
        this.name=names[rand.nextInt(names.length)];
        this.logger=new Logger(this.name,false);
    }

    public String getName() {
        return name;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color){
        if(color.equals("Blue") || color.equals("Red")){
            this.color=color;
        }
    }
    public List<Space> getPossibleStartSpaces(Board board){
        List<Space> result = new ArrayList<>();
        this.boardCopy=board;
        this.playerBombs= new ArrayList<>();
        for(int i=0;i<10;i++){
            for (int j = 0; j < 10; j++) {
//                if(boardCopy.getSpace(i,j).getPawn()!=null) {
                    Pawn currPawn = boardCopy.getSpace(i, j).getPawn();
                    if (boardCopy.getSpace(i, j).getPawn() != null && boardCopy.getSpace(i, j).getPawn().getColor().equals(color) && boardCopy.getSpace(i, j).getPawn().isMovable())
                        result.add(boardCopy.getSpace(i, j));
                    if (boardCopy.getSpace(i, j).getPawn() != null && Objects.equals(boardCopy.getSpace(i, j).getPawn().getType(), "Flag") && !Objects.equals(boardCopy.getSpace(i, j).getPawn().getColor(), color))
                        this.playerFlag = boardCopy.getSpace(i, j);
                    if (boardCopy.getSpace(i, j).getPawn() != null && Objects.equals(boardCopy.getSpace(i, j).getPawn().getType(), "Marshal") && !Objects.equals(boardCopy.getSpace(i, j).getPawn().getColor(), color))
                        this.playerMarshal = boardCopy.getSpace(i, j);
                    if (boardCopy.getSpace(i, j).getPawn() != null && Objects.equals(boardCopy.getSpace(i, j).getPawn().getType(), "Spy") && !Objects.equals(boardCopy.getSpace(i, j).getPawn().getColor(), color))
                        this.playerSpy = boardCopy.getSpace(i, j);
                    if (boardCopy.getSpace(i, j).getPawn() != null && Objects.equals(boardCopy.getSpace(i, j).getPawn().getType(), "Bomb") && !Objects.equals(boardCopy.getSpace(i, j).getPawn().getColor(), color))
                        this.playerBombs.add(boardCopy.getSpace(i, j));
            //    }
            }
        }
        return result;
    }

    public List<Space> calculateMove(HashMap<Space,List<Space>> megaMap){
        megaMap.values().removeIf(Objects::isNull);
        List<Space> optimalMove=lookIntoTheFuture(megaMap);
        if(!optimalMove.isEmpty()&&logger.isPermitted(optimalMove.get(0),optimalMove.get(1))) {
            logger.log(optimalMove.get(0), optimalMove.get(1));
        }
        else {
            Random rand = new Random();
            List<Space> startMoves = new ArrayList(megaMap.keySet());
            Space tempStart = startMoves.get(rand.nextInt(startMoves.size()));
            List<Space> tempTargetMoves = megaMap.get(tempStart);
            Space tempTarget = tempTargetMoves.get(rand.nextInt(tempTargetMoves.size()));
            optimalMove.clear();
            optimalMove.add(tempStart);
            optimalMove.add(tempTarget);
            logger.log(optimalMove.get(0), optimalMove.get(1));
        }
        return optimalMove;
    }
    public List<Space> lookIntoTheFuture(HashMap<Space,List<Space>> megaMap){
       List<Space> result = new ArrayList<>();
        int choiceWeight = 0;
        Space start=null;
        Space target=null;
        for (Map.Entry<Space, List<Space>> spaceListEntry : megaMap.entrySet()) {
            Space space = spaceListEntry.getKey(); // GET START SPACE
            List<Space> value = spaceListEntry.getValue(); // GET LIST OF POSSIBLE MOVES
            for (Space item : value) { // AND ITERATE THROUGH IT
                if (choiceWeight < mostOptimal(space, item)) {
                    choiceWeight = mostOptimal(space, item);
                    start = space;
                    target = item;
                }
            }
        }
        result.add(start);
        result.add(target);
        return result;
    }
    public int mostOptimal(Space start,Space target){
        int finalPoints = 0;
        int baseMovement = calculateDistance(target,playerFlag);
        int killBonus = target.getPawn()!=null ? killCalculator(start,target)*2 : 0;
        int movementBonus = calculateMovementBonus(start);
        int deserterPenalty=0;
        for (int i =0;i<10;i++){
            int tempCalculation = 9-calculateDistance(target,new Space(9,i,new Empty()));
            if(deserterPenalty<tempCalculation){
                deserterPenalty = tempCalculation;
            }
        }

        return finalPoints+baseMovement+killBonus+movementBonus-deserterPenalty;
    }
    public int calculateMovementBonus(Space start){
        int movementBonus = 0;
        Pawn startPawn = start.getPawn();
        switch(startPawn.getRank()){
            case 1->{
                movementBonus+=calculateDistance(start,playerMarshal)/2;
            }
            case 3->{
                int maxVal=0;
                for (Space playerBomb : playerBombs) {
                    maxVal=Math.max(calculateDistance(start,playerBomb),maxVal);
                }
                movementBonus+=maxVal;
            }
            case 10->{
                movementBonus-=calculateDistance(start,playerSpy)/2;
            }
        }
        return movementBonus;
    }

    public int calculateDistance(Space start,Space target){
      //  return (int)(13-Math.hypot(start.getColumn()-target.getColumn(),start.getRow()-target.getRow())); // 13 is the distance between 0,0 and 9,9 EUCLIDEAN DISTANCE
     return (18-Math.abs(start.getColumn()-target.getColumn())+Math.abs(start.getRow()-target.getRow()))/2; // MANHATTAN DISTANCE
    }
    public int killCalculator(Space start,Space target){
        int killBonus = 5;
        Pawn startPawn = start.getPawn();
        Pawn targetPawn = target.getPawn();

        int targetRank = targetPawn.getRank();
        if(startPawn.getRank()>=targetRank) {
            switch (targetRank) {
                case 1 -> killBonus += 30; // SPY
                case 10 -> killBonus += 40; // MARSHAL
                case 11 -> killBonus += 100; // FLAG
                default -> killBonus+=targetRank;
            }
        }
        if(startPawn.getRank()==3&&targetRank==0){
            killBonus+=20;
        }
        return startPawn.getRank()==targetRank?killBonus+9:killBonus;
    }
}




