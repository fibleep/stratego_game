package MenuScreenView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.View;

import java.io.File;
import java.util.List;

public class MenuScreenView extends View {


    private TextField textField;
    private Button newGame;
    private Button rules;
    private Button stats;
    private VBox vbox;
    private Button quit;
    private Parent root;
    private List<Button> buttons;

    public MenuScreenView() {
        initialiseNodes();
        layoutNodes();
    }


    private void initialiseNodes() {
        newGame = new Button("NEW GAME");
        rules = new Button("RULES");
        stats = new Button("STATISTICS");
        quit = new Button("QUIT");
        buttons = List.of(newGame, rules, stats, quit);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setMinSize(300, 100);
            buttons.get(i).setStyle("-fx-background-color: #1F2833;" +
                    "-fx-font-size: 2em;" +
                    "-fx-text-fill: #C5C6C7");
        }
        String path = "resources/AMBITIONS.MP3";

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.05);

    }

    private void layoutNodes() {

        vbox = new VBox();

        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 50));

        vbox.getChildren().addAll(newGame, rules, stats, quit);
        BorderPane.setAlignment(vbox, Pos.CENTER);
        Image menuImg = new Image("images/menu_screen.jpg");
        BackgroundImage bImg = new BackgroundImage(menuImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);
        vbox.setBackground(bGround);
        root = vbox;
    }

    public Button getNewGame() {
        return newGame;
    }

    public Button getRules() {
        return rules;
    }

    public Button getStats() {
        return stats;
    }

    public Button getQuit() {
        return quit;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
