package StartScreenView;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.View;

public class StartScreenView extends View {


    private TextField textField;
    private Button button;
    private Text text;
    private Parent root;

    public StartScreenView() {
        initialiseNodes();
        layoutNodes();
    }


    private void initialiseNodes() {
        textField = new TextField();
        textField.setMaxWidth(90);
        text = new Text("Enter your name");
        text.setStyle("-fx-font: 80 arial;" +
                "-fx-text-fill: #C5C6C7;");
        text.setFill(Color.WHITE);
        button = new Button("START GAME");
    }

    private void layoutNodes() {

        VBox vbox = new VBox();

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        vbox.getChildren().addAll(text, textField, button);
        BorderPane.setAlignment(vbox, Pos.CENTER);
        Image menuImg = new Image("images/battle_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        button.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        button.setMinSize(300, 100);
        vbox.setBackground(bGround);
        root = vbox;

    }

    public TextField getTextField() {
        return textField;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
