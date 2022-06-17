package starter;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import util.Controller;
import MenuScreenView.MenuScreenPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private String team = "team 13";
    private String TITLE = (String.format("STRATEGO TEAM 13 - %s", team));
    private int WIDTH = 1300;
    private int HEIGHT = 1300;
    private static Stage stage;



    @Override
    public void start(Stage stage) {

        MainApplication.stage = stage;
        stage.setTitle(TITLE);
        Screen screen = Screen.getPrimary();

        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.show();
 switchController(new MenuScreenPresenter());
//switchController(new Board());

    }

    public static void switchController(Controller controller) {
        stage.setScene(new Scene(controller.getView().getRoot()));
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
