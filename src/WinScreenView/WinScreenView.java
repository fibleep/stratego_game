package WinScreenView;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.View;

public class WinScreenView extends View {
    private Parent root;
    private Button exit;
    private Text text;
    private VBox vBox;
    private Button stats;
    private Button startAgain;
    private BorderPane borderPane;

    public WinScreenView() {
        initialiseNodes();
        layourNodes();
    }


    private void initialiseNodes() {
        exit = new Button("MAIN MENU");
        text = new Text("You are victorious");
        startAgain = new Button("PLAY AGAIN");
        stats = new Button("SHOW STATS");
        vBox = new VBox();
        borderPane = new BorderPane();
    }


    private void layourNodes() {
        vBox.getChildren().addAll(text, exit, startAgain, stats);

        exit.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7");
        startAgain.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7");
        stats.setStyle("-fx-background-color: #1F2833;" +
                "-fx-font-size: 2em;" +
                "-fx-text-fill: #C5C6C7");
        text.setStyle("-fx-font: 85 arial;");
        text.setFill(Color.WHITE);

        Image menuImg = new Image("images/menu_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        borderPane.setCenter(vBox);

        borderPane.setBackground(bGround);

        root = borderPane;

    }

    @Override
    public Parent getRoot() {
        return root;
    }

    public Button getExit() {
        return exit;
    }

    public Text getText() {
        return text;
    }

    public Button getStats() {
        return stats;
    }

    public Button getStartAgain() {
        return startAgain;
    }
}
