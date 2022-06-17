package BoardView;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.AI;
import models.Player;
import util.View;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends View {

    VBox vBox = new VBox();
    private VBox vbox2;
    private Parent root;
    private HBox hBox2;
    private Button button;
    private List<Button> boardButtons;
    private Button menuButton;
    private Button forfeit;
    private ListView listView;
    private ListView listView2;
    private static final int MAX_BUTTON_SIZE = 100;
    private Text text;
    private Label label;
    private Label label2;
    private Player player;
    private AI ai;


    public BoardView(Player player, AI AI) {
        this.player = player;
        this.ai = AI;
        initialiseNodes();
        layoutNodes();
    }


    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public List<Button> getBoardButtons() {
        return boardButtons;
    }


    private void layoutNodes() {


        VBox vBox1 = new VBox();
        hBox2 = new HBox();
        HBox FirstRow = new HBox();
        HBox hBox = new HBox();
        vBox = new VBox();
        listView = new ListView();
        listView2 = new ListView();
        label = new Label(ai.getName());
        label2 = new Label(player.getName());

        listView.setPrefWidth(500);
        listView2.setPrefWidth(500);
        listView.setPrefHeight(200);
        listView2.setPrefHeight(200);

        HBox[] setupButtonColumns = new HBox[10];
        VBox setupBottonsVbox = new VBox();

        FirstRow.setMinHeight(200);
        FirstRow.setMinWidth(200);
        text = new Text();
        int setupButtonsPerColumn = 10;
        int setupNr = 0;
        fillButtonColumns:
        for (int i = 0; i < 10; i++) {
            setupButtonColumns[i] = new HBox();
            ObservableList<Node> contents = setupButtonColumns[i].getChildren();
            for (int j = 0; j < setupButtonsPerColumn; j++) {
                if (setupNr == 100) break fillButtonColumns;
                contents.add(boardButtons.get(setupNr++));

            }
            setupButtonColumns[i].setAlignment(Pos.CENTER);
            setupBottonsVbox.getChildren().add(setupButtonColumns[i]);

        }

        setupBottonsVbox.setAlignment(Pos.CENTER);

        ObservableList<Node> row = hBox.getChildren();
        row.addAll(setupBottonsVbox);
        vBox.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(text);
        hBox2.getChildren().addAll(menuButton, forfeit);
        vbox2.getChildren().addAll(hBox2, label, listView, label2, listView2);

        vbox2.setTranslateX(100);

        menuButton.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7;" +
                "-fx-border-color: #0b0c10; -fx-border-width: 2px;");
        forfeit.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7;" +
                "-fx-border-color: #0b0c10; -fx-border-width: 2px;");


        listView.setStyle("-fx-control-inner-background: gray;" +
                "  -fx-control-inner-background-alt: derive(-fx-control-inner-background, 70%);");
        listView2.setStyle("-fx-control-inner-background: gray;" +
                "  -fx-control-inner-background-alt: derive(-fx-control-inner-background, 70%);");

        label.setTextFill(Color.color(1, 0, 0));
        label.setStyle("-fx-font-size: 2em;");
        label2.setTextFill(Color.color(1, 0, 0));
        label2.setStyle("-fx-font-size: 2em;");
        vbox2.setSpacing(15);
        vbox2.setAlignment(Pos.TOP_RIGHT);

        vBox.setAlignment(Pos.CENTER);
        vBox1.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(vBox, vBox1, vbox2);
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        Image menuImg = new Image("images/battle_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        hBox.setBackground(bGround);
        root = hBox;
    }

    private void initialiseNodes() {
        boardButtons = buildBoardButtons();
        button = new Button(" DWA");
        menuButton = new Button("MAIN MENU");
        forfeit = new Button("SURRENDER");
        vbox2 = new VBox();
    }

    private List<Button> buildBoardButtons() {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Button button = new Button();
            button.setPrefSize(MAX_BUTTON_SIZE, MAX_BUTTON_SIZE);
            button.setStyle("-fx-background-color: #1F2833;" +
                    "-fx-border-color: #0b0c10; -fx-border-width: 2px;");
            buttons.add(button);
        }
        return buttons;
    }

    public ListView getListView() {
        return listView;
    }

    public ListView getListView2() {
        return listView2;
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public Button getForfeit() {
        return forfeit;
    }

    public Parent getRoot() {
        return root;
    }
}
