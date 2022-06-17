package ChooseTeamView;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.View;

public class ChooseTeamView extends View {

    private Button redButton;
    private Button blueButton;
    private HBox hbox;
    private VBox vBox;
    private Text text;
    private Parent root;

    public ChooseTeamView() {
        initialiseNodes();
        layoutNodes();
    }


    private void initialiseNodes() {
        redButton = new Button("");
        blueButton = new Button("");
        hbox = new HBox();
        vBox = new VBox();
    }

    private void layoutNodes() {
        blueButton.setStyle("-fx-background-color: #0000ff; ");
        blueButton.setTextFill(Color.WHITE);
        redButton.setStyle("-fx-background-color: #ff0000; ");
        redButton.setTextFill(Color.WHITE);
        redButton.setMaxWidth(200);
        redButton.setMaxHeight(200);
        redButton.setMinHeight(200);
        redButton.setMinWidth(200);
        blueButton.minHeight(200);
        blueButton.setMaxWidth(200);
        blueButton.setMaxHeight(200);
        blueButton.setMinWidth(200);

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        hbox.setTranslateX(30);
        hbox.setTranslateY(30);
        hbox.getChildren().addAll(redButton, blueButton);
        hbox.setSpacing(400);
        vBox.getChildren().addAll(hbox);

        Image menuImg = new Image("images/battle_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        vBox.setBackground(bGround);

        root = vBox;
    }


    public Button getRedButton() {
        return redButton;
    }

    public void setRedButton(Button redButton) {
        this.redButton = redButton;
    }

    public Button getBlueButton() {
        return blueButton;
    }

    public void setBlueButton(Button blueButton) {
        this.blueButton = blueButton;
    }


    public void setRoot(Parent root) {
        this.root = root;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
