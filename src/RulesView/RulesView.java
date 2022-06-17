package RulesView;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import models.Rank;
import util.View;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.white;

public class RulesView extends View {

    private Parent root;
    private HBox hBox;
    private HBox hBoxtext;
    private HBox hBox2;
    private HBox hBox3;
    private HBox hBox4;
    private HBox hBox5;
    private HBox hBox6;
    private HBox hBoxtext2;
    private VBox vbox;
    private VBox vBox3;
    private VBox vbox2;
    private HBox hbox7;
    private Button boardAndSetup;
    private Button movement;
    private Button attack;
    private Button goal;
    private Button hints;

    private Text text;
    private Text text2;
    private Text text3;
    private Text text4;
    private Text text5;
    private Text text6;
    private BorderPane borderPane;
    private List<Button> rankButtons;
    private List<Button> rankButtons2;
    private List<Button> rankButtons3;
    private Button exit;
    private static final String IMAGES_DIRECTORY = "images/";
    private static final int RANK_BUTTON_SIZE = 125;


    public RulesView() {
        initialiseNodes();
        layoutNodes();
    }


    public Button getExit() {
        return exit;
    }

    //0 is flag
    // 11 bomb
    private void layoutNodes() {
        hBox.getChildren().addAll(rankButtons.get(1), rankButtons.get(2), rankButtons.get(3)
                , rankButtons.get(4), rankButtons.get(5), rankButtons.get(6), rankButtons.get(7), rankButtons.get(8), rankButtons.get(9)
                , rankButtons.get(10));

        hBox2.getChildren().addAll(rankButtons.get(0), rankButtons.get(11));

        hBox3.getChildren().addAll(rankButtons2.get(1), attackButton(), rankButtons2.get(10));
        hBox4.getChildren().addAll(rankButtons2.get(3), attackButton(), rankButtons2.get(11));
        hBox5.getChildren().addAll(rankButtons2.get(6), attackButton(), rankButtons3.get(6));

        hBox.setTranslateY(50);
        hBox2.setTranslateY(50);
        hBoxtext.setTranslateY(50);
        hBoxtext2.setTranslateY(50);




        // text4,hBox3,text3,hBox4,text6,hBox5;


        hBoxtext.getChildren().addAll(text);
        hBox.setAlignment(Pos.CENTER);
        hBoxtext.setAlignment(Pos.CENTER);
        text.setStyle("-fx-font: 25 arial;");
        text2.setStyle("-fx-font: 25 arial;");
        text3.setStyle("-fx-font: 25 arial;");
        text4.setStyle("-fx-font: 25 arial;");
        text5.setStyle("-fx-font: 20 arial;");
        text.setFill(Color.WHITE);
        text4.setFill(Color.WHITE);
        text6.setFill(Color.WHITE);
        text2.setFill(Color.WHITE);
        text3.setFill(Color.WHITE);
        text5.setFill(Color.WHITE);

        text6.setStyle("-fx-font: 25 arial;");
        attack.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        movement.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        hints.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        boardAndSetup.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        goal.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");

        exit.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        exit.setMinSize(300, 100);
        hBoxtext2.getChildren().addAll(text2);

        text5.setTranslateY(150);

        hBoxtext2.setAlignment(Pos.CENTER);


        hBox2.setAlignment(Pos.CENTER);
        hBox6.getChildren().add(exit);
        hBox6.setAlignment(Pos.CENTER);


        vbox.getChildren().addAll(hBoxtext,hBox);
        vbox.setSpacing(20);
        vBox3.getChildren().addAll(hBoxtext2,hBox2);
        vBox3.setSpacing(20);
        hbox7.getChildren().addAll(vbox,vBox3);
        hbox7.setSpacing(60);
        hbox7.setAlignment(Pos.CENTER);

        //,,text4,hBox3,text3,hBox4,text6,hBox5,exit
        vbox2.getChildren().addAll(text4, hBox3, text3, hBox4, text6, hBox5);
        vbox2.setSpacing(10);

        vbox2.setTranslateY(100);
        // text5.setTranslateY(-100);

        text5.setWrappingWidth(400);

       // text5.setTranslateY(-170);
        borderPane.setTop(hbox7);
        borderPane.setLeft(vbox2);


        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardAndSetup,attack,hints,goal,movement);
        vbox.setTranslateY(200);

        boardAndSetup.setPrefWidth(200);
        attack.setPrefWidth(200);
        hints.setPrefWidth(200);
        goal.setPrefWidth(200);
        movement.setPrefWidth(200);
        HBox hboxButtons = new HBox();
        hboxButtons.getChildren().addAll(vbox,text5);
        hboxButtons.setSpacing(20);
        hboxButtons.setTranslateX(100);

        borderPane.setCenter(hboxButtons);
        borderPane.setBottom(hBox6);
        root = borderPane;
    }

    private void initialiseNodes() {

        rankButtons = buildRankButtons();
        rankButtons2 = buildRankButtons();
        rankButtons3 = buildRankButtons();

        this.hBox = new HBox();
        this.hBoxtext = new HBox();
        this.hBox2 = new HBox();
        this.hBox3 = new HBox();
        this.hBox4 = new HBox();
        this.vBox3 = new VBox();
        this.hBox5 = new HBox();
        this.hBox6 = new HBox();
        this.hbox7 = new HBox();
        this.hBoxtext2 = new HBox();
        this.exit = new Button("MAIN MENU");
        this.borderPane = new BorderPane();
        this.vbox = new VBox();
        this.vbox2 = new VBox();
        this.attack = new Button("ATTACK");
        this.goal = new Button("GOAL");
        this.movement = new Button("MOVEMENT");
        this.boardAndSetup = new Button("BOARD");
        this.text = new Text("MOVABLE PAWNS");
        this.hints = new Button("HINTS");


        this.text2 = new Text("Not Movable Pawns");
        this.text3 = new Text("Miner kills Bomb");
        this.text4 = new Text("Spy kills Marshal");
        this.text5 = new Text("The purpose of the game is to capture the enemy flag.\nOften the flag is surrounded" +
                " by bombs to protect it.\nYou start with 40 pawns with different kind of powers.\nFor example the scout can" +
                " move multiple tiles\nand is used to explore the map so you can\nsend your more powerful units in more " +
                "safely.\n");
        this.text6 = new Text("Same level both die");
        Image menuImg = new Image("images/battle_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        borderPane.setBackground(bGround);
        vbox.setSpacing(5);
    }

    private java.util.List<javafx.scene.control.Button> buildRankButtons() {
        List<javafx.scene.control.Button> buttons = new ArrayList<>();
        for (Rank rank : Rank.values()) {

            String imageFile = IMAGES_DIRECTORY + rank.toString() + "R.png";
            ImageView view = new ImageView(new Image(imageFile));
            view.setFitHeight(RANK_BUTTON_SIZE);
            view.setPreserveRatio(true);
            javafx.scene.control.Button button = new Button();
            button.setPrefSize(RANK_BUTTON_SIZE, RANK_BUTTON_SIZE);
            button.setGraphic(view);
            button.setStyle("-fx-background-color: #1F2833;" +
                    "-fx-border-color: #0b0c10; -fx-border-width: 2px;");
            buttons.add(button);
        }
        return buttons;
    }

    private Button attackButton() {

        String imageFile = IMAGES_DIRECTORY + "sword" + ".png";
        ImageView view = new ImageView(new Image(imageFile));
        view.setFitHeight(RANK_BUTTON_SIZE);
        view.setPreserveRatio(true);
        javafx.scene.control.Button button = new Button();
        button.setPrefSize(RANK_BUTTON_SIZE, RANK_BUTTON_SIZE);
        button.setGraphic(view);
        button.setStyle("-fx-background-color: #1F2833;" +
                "-fx-border-color: #0b0c10; -fx-border-width: 2px;");
        return button;
    }

    public Button getBoardAndSetup() {
        return boardAndSetup;
    }

    public Button getMovement() {
        return movement;
    }

    public Button getAttack() {
        return attack;
    }

    public Button getGoal() {
        return goal;
    }

    public Button getHints() {
        return hints;
    }

    public Text getText5() {
        return text5;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
