package SetupView;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import models.Rank;
import util.View;

import java.util.ArrayList;
import java.util.List;

public class SetupView extends View {
    private static final int RANK_BUTTON_SIZE = 150;
    private static final String IMAGES_DIRECTORY = "images/";

    private List<Button> rankButtons;
    private List<Button> setupButtons;

    private Button StartGame;
    private Button AutoLayout;

    private Parent root;

    public SetupView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        SetupPresenter.player.getColor();
        rankButtons = buildRankButtons();
        setupButtons = buildSetupButtons();
        StartGame = new Button("START");
        AutoLayout = new Button("AUTOLAYOUT");
        StartGame.setMinSize(300, 100);
        StartGame.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7;-fx-border-color: #0b0c10; -fx-border-width: 2px;");
        AutoLayout.setMinSize(300, 100);
        AutoLayout.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7;-fx-border-color: #0b0c10; -fx-border-width: 2px;");

    }

    private List<Button> buildSetupButtons() {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 40; i++) {

            Button button = new Button();
            button.setPrefSize(RANK_BUTTON_SIZE, RANK_BUTTON_SIZE);
            button.setStyle("-fx-background-color: #1F2833;" +
                    "-fx-border-color: #0b0c10; -fx-border-width: 2px; -fx-text-fill: #C5C6C7;");
            buttons.add(button);
        }
        return buttons;
    }

    private List<Button> buildRankButtons() {
        int count;
        List<Button> buttons = new ArrayList<>();
        for (Rank rank : Rank.values()) {

            count = rank.getCount();
            String imageFile = IMAGES_DIRECTORY + rank.toString().toLowerCase() + SetupPresenter.player.getColor().charAt(0) + ".png";
            ImageView view = new ImageView(new Image(imageFile));
            view.setFitHeight(RANK_BUTTON_SIZE);
            view.setPreserveRatio(true);
            Button button = new Button("" + count--);
            button.setPrefSize(RANK_BUTTON_SIZE, RANK_BUTTON_SIZE);
            button.setStyle("-fx-background-color: #1F2833;" +
                    "-fx-border-color: #0b0c10; -fx-border-width: 2px; -fx-text-fill: #C5C6C7;");
            button.setGraphic(view);
            buttons.add(button);
        }
        return buttons;
    }

    public Button getStartGame() {
        return StartGame;
    }

    public void setStartGame(Button startGame) {
        StartGame = startGame;
    }

    public Button getAutoLayout() {
        return AutoLayout;
    }

    public void setAutoLayout(Button autoLayout) {
        AutoLayout = autoLayout;
    }

    private void layoutNodes() {
        VBox[] buttonColumns = new VBox[2];
        HBox[] setupButtonColumns = new HBox[4];
        VBox setupBottonsVbox = new VBox();
        HBox FirstRow = new HBox();
        HBox hBox = new HBox();
        VBox vBox5 = new VBox();

        FirstRow.setMinHeight(150);
        FirstRow.setMinWidth(150);


        int setupButtonsPerColumn = 10;
        int setupNr = 0;
        fillButtonColumns:
        for (int i = 0; i < 4; i++) {
            setupButtonColumns[i] = new HBox();
            ObservableList<Node> contents = setupButtonColumns[i].getChildren();
            for (int j = 0; j < setupButtonsPerColumn; j++) {
                if (setupNr == 40) break fillButtonColumns;
                contents.add(setupButtons.get(setupNr++));

            }
            setupButtonColumns[i].setAlignment(Pos.CENTER);
            setupBottonsVbox.getChildren().add(setupButtonColumns[i]);

        }

        setupBottonsVbox.getChildren().add(StartGame);
        setupBottonsVbox.getChildren().add(AutoLayout);
        setupBottonsVbox.setAlignment(Pos.CENTER);

        int buttonsPerColumn = rankButtons.size() / buttonColumns.length;
        int buttonNr = 0;
        fillButtonColumns:
        for (int i = 0; i < buttonColumns.length; i++) {
            buttonColumns[i] = new VBox();
            ObservableList<Node> contents = buttonColumns[i].getChildren();
            for (int j = 0; j < buttonsPerColumn; j++) {
                if (buttonNr == rankButtons.size()) break fillButtonColumns;
                contents.add(rankButtons.get(buttonNr++));
            }
            buttonColumns[i].setAlignment(Pos.CENTER);
        }
        ObservableList<Node> row = hBox.getChildren();
        row.addAll(buttonColumns);
        row.addAll(setupBottonsVbox);
        row.add(vBox5);


        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        Image menuImg = new Image("images/battle_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        hBox.setBackground(bGround);

        root = hBox;
    }


    @Override
    public Parent getRoot() {
        return root;
    }

    public List<Button> getRankButtons() {
        return rankButtons;
    }

    public List<Button> getSetupButtons() {
        return setupButtons;
    }


    public void setVisibleRankButtons() {
        for (Button button : rankButtons) {
            button.setVisible(true);
        }

    }
}
