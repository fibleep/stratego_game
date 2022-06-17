package BoardView;

import Database.Database;
import MenuScreenView.MenuScreenPresenter;
import StatScreenView.StatScreenPresenter;
import WinScreenView.WinScreenPresenter;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;
import starter.MainApplication;
import util.Controller;
import util.View;

import java.text.ParseException;
import java.util.*;

public class BoardPresenter extends Controller {

    private final int PAWN_SIZE = 100;
    private final Database SGDB = new Database();
    private final Board board;
    private List<Button> buttons;
    private final BoardView view;
    private Pawn currentPawn;
    private final boolean enemyTurn = true;
    private Pawn[] pawns;
    private final Player player;
    private final boolean currentPawnSelected = false;
    private final StringBuilder turn;
    private final List<String> moves = new ArrayList<>();
    private final long startTime;
    private long endTime;
    private boolean isDone = false;
    private final AI AI;
    private String selectedPawnColor;

    private Space startSpace;


    private boolean isPlayerTurn = true;
    private boolean turnInProgress = false;
    private boolean pawnToMove = false;
    private boolean aiInit;

    public BoardPresenter(Board board, Player player, AI AI) {
        this.player = player;
        this.AI = AI;
        this.board = board;
        turn = new StringBuilder();
        view = new BoardView(player, AI);
        if (Objects.equals(this.player.getColor(), "Blue")) {
            aiInit = true;
        }
        this.startTime = System.currentTimeMillis();
        //if you c button out of the array of buttons it will select the chosen button with this loop
        for (int i = 0; i < 100; i++) {
            int finalI = i;

            view.getMenuButton().setOnAction(actionEvent -> {
                SGDB.dbSaveGame(false);
                MainApplication.switchController(new MenuScreenPresenter());
            });
            view.getForfeit().setOnAction(actionEvent -> {
                SGDB.dbSaveGame(false);
                MainApplication.switchController(new WinScreenPresenter(false, player));
            });

            view.getBoardButtons().get(i).setOnAction(actionEvent -> {
                try {
                    selectPawn(finalI);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

        }
        displayBoard(board);
        SGDB.dbInitialize(player);
        player.getLogger().setAccessPoint(SGDB);
        AI.getLogger().setAccessPoint(SGDB);
        displayBoard(board);
        showLog();
        if (aiInit) {
            aiInit = false;
            try {
                computerTurn();
                displayBoard(board);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void showLog() {
        String turnLog = "";

        int index = 0;
        int index2 = 0;

        for (Map.Entry<String, Integer> entry : AI.getLogger().getTurnLog().entrySet()) {
            for (int i = index; i < view.getListView().getItems().size(); i++) {
                view.getListView().getItems().remove(i);
            }
            view.getListView().getItems().add(index++, turnLog + entry.getKey());

        }


        for (Map.Entry<String, Integer> entry : player.getLogger().getTurnLog().entrySet()) {
            for (int i = index2; i < view.getListView2().getItems().size(); i++) {
                view.getListView2().getItems().remove(i);
            }
            view.getListView2().getItems().add(index2++, turnLog + entry.getKey());
        }
    }

    private void displayBoard(Board board) {
        emptyBoard();
        //the reason for this loop is to make the board start at 0 on the top left of the board otherwise it
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                try {
                    int number = i * 10 + j;
                    ImageView view = null;

                    try {
                        if (Objects.equals(board.getSpace(calculateColumn(number), calculateRow(number)).getPawn().getColor(), player.getColor()) || board.getSpace(calculateColumn(number), calculateRow(number)).getPawn().isKnown()) {
                            Pawn currPawn = board.getSpace(calculateColumn(number), calculateRow(number)).getPawn();
                            view = new ImageView(new Image("/images/" + currPawn.getType().toLowerCase() + currPawn.getColor().charAt(0) + ".png"));
                        }
                    } catch (Exception ignored) {
                    }
                    ;
                    try {
                        if (board.getSpace(calculateColumn(number), calculateRow(number)).getPawn().getColor() == AI.getColor() && !board.getSpace(calculateColumn(number), calculateRow(number)).getPawn().isKnown()) {
                            view = new ImageView(new Image("/images/" + AI.getColor() + ".png"));
                        }
                    } catch (Exception ignored) {
                    }
                    ;
                    try {
                        if (Objects.equals(board.getSpace(calculateColumn(number), calculateRow(number)).getEntity().getType(), "Lake")) {
                            view = new ImageView(new Image("/images/Lake.png"));
                        }
                    } catch (Exception ignored) {
                    }
                    view.setFitHeight(PAWN_SIZE);
                    view.setPreserveRatio(true);
                    this.view.getBoardButtons().get(number).setMinWidth(PAWN_SIZE);
                    this.view.getBoardButtons().get(number).setMinHeight(PAWN_SIZE);
                    this.view.getBoardButtons().get(number).setGraphic(view);
                    this.view.getBoardButtons().get(number).setPrefSize(PAWN_SIZE, PAWN_SIZE);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void emptyBoard() {
        for (int i = 0; i < 100; i++) {
            view.getBoardButtons().get(i).setGraphic(null);
        }
    }

    private int calculateRow(int space) {
        return 9 - (space > 9 ? Integer.parseInt(Integer.toString(space).substring(1, 2)) : space);
    }

    private int calculateColumn(int space) {
        return 9 - (space / 10);
    }


    public void selectPawn(int space) throws ParseException {
        int column = calculateColumn(space);
        int row = calculateRow(space);
        Space workSpace = board.getSpace(column, row);
        if (!pawnToMove) {
            boolean isPiece = workSpace.getEntity().isPlaySpace() && workSpace.getEntity().isMovable();
            if (isPiece) {
                Pawn currentPawn = (Pawn) workSpace.getEntity();
                selectedPawnColor = currentPawn.getColor();
                if ((isPlayerTurn && selectedPawnColor.equals(player.getColor()))) {
                    this.currentPawn = currentPawn;
                    this.startSpace = workSpace;
                    pawnToMove = true;
                }
            }
        } else {
            List<Space> possibleMovements = possibleMovements(startSpace, currentPawn);
            pawnToMove = false;
            if (isValid(workSpace, possibleMovements) && player.getLogger().isPermitted(startSpace, workSpace)) {
                player.getLogger().log(startSpace, workSpace);
                movePawn(startSpace, workSpace);
                currentPawn = null;
                displayBoard(board);
                if (this.isPlayerTurn && !turnInProgress) {
                    this.isPlayerTurn = false;
                    computerTurn();
                    displayBoard(board);
                    showLog();
                }
            }
        }

    }

    public void computerTurn() throws ParseException {
        this.turnInProgress = true;
        List<Space> allStartSpaces = AI.getPossibleStartSpaces(board);
        HashMap<Space, List<Space>> megaMap = new HashMap<>();
        for (int i = 0; i < allStartSpaces.size(); i++) {
            megaMap.put(allStartSpaces.get(i), possibleMovements(allStartSpaces.get(i), allStartSpaces.get(i).getPawn()));
        }
        List<Space> finalDecision = AI.calculateMove(megaMap);
        System.out.println(finalDecision);
        movePawn(finalDecision.get(0), finalDecision.get(1));
        this.turnInProgress = false;
        this.isPlayerTurn = true;

    }

    /**
     * Moves pawns based on result of move checker.
     *
     * @param startSpace  first pawn clicked
     * @param targetSpace 2nd space/pawn clicked
     */
    public void movePawn(Space startSpace, Space targetSpace) throws ParseException {
        int superBoolean = Objects.equals(targetSpace.getEntity().getType(), "Empty") ? 0 : moveChecker(startSpace.getPawn(), targetSpace.getPawn());
        switch (superBoolean) {
            case 0 -> {
                if (targetSpace.getPawn() != null && Objects.equals(targetSpace.getPawn().getType(), "Flag")) {
                    MainApplication.switchController(new StatScreenPresenter());
                    //   Platform.exit();
                }
                board.getSpace(targetSpace.getRow(), targetSpace.getColumn()).setEntity(startSpace.getEntity());
                board.getSpace(startSpace.getRow(), startSpace.getColumn()).setEntity(new Empty());
            }
            case 1 -> {
                board.getSpace(targetSpace.getRow(), targetSpace.getColumn()).setEntity(new Empty());
                board.getSpace(startSpace.getRow(), startSpace.getColumn()).setEntity(new Empty());
            }
            case 2 -> board.getSpace(startSpace.getRow(), startSpace.getColumn()).setEntity(new Empty());
        }
    }
    /**
     * checks if not lake, own pawn
     *
     * @param chosenSpace
     * @param possibleMovements
     * @return
     */
    public boolean isValid(Space chosenSpace, List<Space> possibleMovements) {
        if (chosenSpace.getEntity().isPlaySpace()) {
            if (possibleMovements != null && possibleMovements.contains(chosenSpace)) {
                if (chosenSpace.getPawn() != null) {
                    Pawn targetPawn = chosenSpace.getPawn();
                    return !Objects.equals(targetPawn.getColor(), currentPawn.getColor());
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int moveChecker(Pawn currentPawn, Pawn targetPawn) {
        int targetRank = targetPawn.getRank();
        int superBoolean = 0;
        boolean isSpecial = switch (targetRank) {
            case 0, 11, 10 -> true;
            default -> false;
        };
        if (!isSpecial) {
            superBoolean = currentPawn.getRank() > targetRank ? 0 : currentPawn.getRank() == targetRank ? 1 : 2;
        } else {
            switch (targetRank) {
                case 0 -> {
                    if (currentPawn.getRank() == 3) superBoolean = 0;
                    else superBoolean = 2;
                    break;
                }
                case 10 -> {
                    if (currentPawn.getRank() == 1) superBoolean = 0;
                    else if (currentPawn.getRank() == 10) superBoolean = 1;
                    else superBoolean = 2;
                }
                case 11 -> {
                    superBoolean = 0;
                    isDone = true;
                }
            }
        }
        return superBoolean;
    }

    public List<Space> possibleMovements(Space startSpace, Pawn currentPawn) {
        List<Space> possibleMovements = new ArrayList<Space>();
        if (startSpace.getRow() > 0)
            possibleMovements.add(board.getSpace(startSpace.getRow() - 1, startSpace.getColumn()));
        if (startSpace.getRow() < 9)
            possibleMovements.add(board.getSpace(startSpace.getRow() + 1, startSpace.getColumn()));
        if (startSpace.getColumn() > 0)
            possibleMovements.add(board.getSpace(startSpace.getRow(), startSpace.getColumn() - 1));
        if (startSpace.getColumn() < 9)
            possibleMovements.add(board.getSpace(startSpace.getRow(), startSpace.getColumn() + 1));
        if (currentPawn.getType().equals("Scout")) {
            int startY = startSpace.getRow();
            int startX = startSpace.getColumn();
            List<Space> temp = new ArrayList<>();
            //Y LOW BOUND
            for (int i = 0; i < startY; i++) {
                Space y = board.getSpace(i, startX);
                temp.add(y);
                if (Objects.equals(y.getEntity().getType(), "Lake")) {
                    temp.removeAll(temp);
                } else if (!Objects.equals(y.getEntity().getType(), "Empty")) {
                    temp.removeAll(temp);
                    temp.add(y);
                }
            }
            if (!temp.isEmpty()) possibleMovements.addAll(temp);
            temp.removeAll(temp);

            //Y HIGH BOUND
            for (int i = 9; startY < i; i--) {
                Space y = board.getSpace(i, startX);
                temp.add(y);
                if (Objects.equals(y.getEntity().getType(), "Lake")) {
                    temp.removeAll(temp);
                } else if (!Objects.equals(y.getEntity().getType(), "Empty")) {
                    temp.removeAll(temp);
                    ;
                    temp.add(y);
                }
            }
            if (!temp.isEmpty()) possibleMovements.addAll(temp);
            temp.removeAll(temp);
            // X LOW BOUND
            for (int i = 0; i < startX; i++) {
                Space x = board.getSpace(startY, i);
                temp.add(x);
                if (Objects.equals(x.getEntity().getType(), "Lake")) {
                    temp.removeAll(temp);
                } else if (!Objects.equals(x.getEntity().getType(), "Empty")) {
                    temp.removeAll(temp);
                    temp.add(x);
                }
            }
            if (!temp.isEmpty()) possibleMovements.addAll(temp);
            temp.removeAll(temp);

            // X HIGH BOUND
            for (int i = 9; startX < i; i--) {
                Space x = board.getSpace(startY, i);
                temp.add(x);
                if (Objects.equals(x.getEntity().getType(), "Lake")) {
                    temp.removeAll(temp);
                } else if (!Objects.equals(x.getEntity().getType(), "Empty")) {
                    temp.removeAll(temp);
                    temp.add(x);
                }
            }
            if (!temp.isEmpty()) possibleMovements.addAll(temp);
            temp.removeAll(temp);
        }
        possibleMovements.removeIf(space -> Objects.equals(space.getEntity().getType(), "Lake") || (space.getPawn() != null && space.getPawn().getColor().equals(currentPawn.getColor())));
        return possibleMovements.isEmpty() ? null : possibleMovements;
    }

    @Override
    public View getView() {
        return view;
    }
}
