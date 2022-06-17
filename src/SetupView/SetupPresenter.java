package SetupView;

import BoardView.BoardPresenter;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;
import models.Board;
import starter.MainApplication;
import util.Controller;
import util.Util;
import util.View;

import java.util.List;
import java.util.Objects;

public class SetupPresenter extends Controller {

    private SetupView view;
    private String currentSelectedPawn;
    public static Player player;
    private final AI AI;
    private String[] playerSetup;


    private Board board;


    public SetupPresenter(Player player) {
        this.player = player;
        this.view = new SetupView();
        this.AI = new AI();
        AI.setColor(Objects.equals(player.getColor(), "Red") ? "Blue" : "Red");
        List<Button> rankbuttons = view.getRankButtons();
        List<Button> setupButtons = view.getSetupButtons();
        this.playerSetup = new String[40];

        for (int i = 0; i < rankbuttons.size(); i++) {
            int finalI = i;
            rankbuttons.get(i).setOnAction(actionEvent -> currentSelectedPawn = Util.capitalise(Rank.values()[finalI].toString()));
        }
        for (int i = 0; i < 40; i++) {
            int finalI = i;
            setupButtons.get(i).setOnAction(actionEvent -> setTile(finalI, setupButtons.get(finalI)));
        }
        board = new Board(player, AI);
        view.getStartGame().setOnAction(actionEvent -> switchScreen());
        view.getAutoLayout().setOnAction(actionEvent -> autoLayout());
    }

    private void autoLayout() {
        int[] playerSetupRandom = board.randomizeBoard();
        for (int i = 0; i < 40; i++) {
            playerSetup[i] = board.decodeType(playerSetupRandom[i]);
        }
        board.buildBoard(playerSetupRandom, board.randomizeBoard());
        updateCount();
        MainApplication.switchController(new BoardPresenter(board, player, AI));
    }

    private void switchScreen() {
        int[] playerSetupTranslated = new int[40];
        for (int i = 0; i < 40; i++) {
            playerSetupTranslated[39 - i] = board.encodeType(playerSetup[i]);
        }
        board.buildBoard(playerSetupTranslated, board.randomizeBoard());
        MainApplication.switchController(new BoardPresenter(board, player, AI));
    }

    private void setTile(int tileNumber, Button button) {

        updateTiles(button,tileNumber);
        updateCount();
        currentSelectedPawn = "";
    }

    private void updateCount() {

        int countFlag = Rank.FLAG.getCount();
        int countBomb = Rank.BOMB.getCount();
        int countColonel = Rank.COLONEL.getCount();
        int countLieutenant = Rank.LIEUTENANT.getCount();
        int countMarshal = Rank.MARSHAL.getCount();
        int countMiner = Rank.MINER.getCount();
        int countScout = Rank.SCOUT.getCount();
        int countSpy = Rank.SPY.getCount();
        int countMajor = Rank.MAJOR.getCount();
        int countGeneral = Rank.GENERAL.getCount();
        int countCaptain = Rank.CAPTAIN.getCount();
        int countSergeant = Rank.SERGEANT.getCount();

        for (int i = 0; i < 40; i++) {
            String rank = playerSetup[i];
            if (rank == null || rank.isEmpty()) continue;
            view.setVisibleRankButtons();
            switch (rank) {
                case "Flag" -> countFlag--;
                case "Captain" -> countCaptain--;
                case "Colonel" -> countColonel--;
                case "Bomb" -> countBomb--;
                case "General" -> countGeneral--;
                case "Lieutenant" -> countLieutenant--;
                case "Major" -> countMajor--;
                case "Miner" -> countMiner--;
                case "Scout" -> countScout--;
                case "Sergeant" -> countSergeant--;
                case "Spy" -> countSpy--;
                case "Marshal" -> countMarshal--;
            }

            if (countFlag == 0) {
                view.getRankButtons().get(0).setVisible(false);
            } else {
                view.getRankButtons().get(0).setText("" + countFlag);
            }

            if (countSpy == 0) {
                view.getRankButtons().get(1).setVisible(false);
            } else {
                view.getRankButtons().get(1).setText("" + countSpy);
            }
            if (countGeneral == 0) {
                view.getRankButtons().get(9).setVisible(false);
            } else {
                view.getRankButtons().get(9).setText("" + countGeneral);
            }
            if (countLieutenant == 0) {
                view.getRankButtons().get(5).setVisible(false);
            } else {
                view.getRankButtons().get(5).setText("" + countLieutenant);
            }
            if (countMajor == 0) {
                view.getRankButtons().get(7).setVisible(false);
            } else {
                view.getRankButtons().get(7).setText("" + countMajor);
            }
            if (countCaptain == 0) {
                view.getRankButtons().get(6).setVisible(false);
            } else {
                view.getRankButtons().get(6).setText("" + countCaptain);
            }
            if (countBomb == 0) {
                view.getRankButtons().get(11).setVisible(false);
            } else {
                view.getRankButtons().get(11).setText("" + countBomb);
            }
            if (countMiner == 0) {
                view.getRankButtons().get(3).setVisible(false);
            } else {
                view.getRankButtons().get(3).setText("" + countMiner);
            }
            if (countScout == 0) {
                view.getRankButtons().get(2).setVisible(false);
            } else {
                view.getRankButtons().get(2).setText("" + countScout);
            }
            if (countMarshal == 0) {
                view.getRankButtons().get(10).setVisible(false);
            } else {
                view.getRankButtons().get(10).setText("" + countMarshal);
            }
            if (countSergeant == 0) {
                view.getRankButtons().get(4).setVisible(false);
            } else {
                view.getRankButtons().get(4).setText("" + countSergeant);
            }
            if (countColonel == 0) {
                view.getRankButtons().get(8).setVisible(false);
            } else {
                view.getRankButtons().get(8).setText("" + countColonel);
            }
        }
    }

    private void updateTiles(Button buttonTile, int tileNumber) {

        try {
            Image imgMajor = new Image("/images/" + currentSelectedPawn.toLowerCase() + player.getColor().charAt(0) + ".png");
            ImageView view = new ImageView(imgMajor);
            view.setFitHeight(150);
            view.setPreserveRatio(true);
            buttonTile.setPrefSize(150, 150);
            buttonTile.setGraphic(view);
            playerSetup[tileNumber] = currentSelectedPawn;

        } catch (Exception e) {
            playerSetup[tileNumber] = "Null";
            buttonTile.setGraphic(null);
        }
    }

    @Override
    public View getView() {
        return view;
    }

}
