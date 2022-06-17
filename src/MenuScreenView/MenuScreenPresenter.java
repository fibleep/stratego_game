package MenuScreenView;

import RulesView.RulesPresenter;
import StartScreenView.StartScreenPresenter;
import StatScreenView.StatScreenPresenter;
import javafx.application.Platform;
import starter.MainApplication;
import util.Controller;
import util.View;

import java.text.ParseException;

public class MenuScreenPresenter extends Controller {

    private MenuScreenView view;

    public MenuScreenPresenter() {
        this.view = new MenuScreenView();
        view.getNewGame().setOnAction(actionEvent -> {
            try {
                switchScene(1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        view.getRules().setOnAction(actionEvent -> {
            try {
                switchScene(2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        view.getStats().setOnAction(actionEvent -> {
            try {
                switchScene(3);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        view.getQuit().setOnAction(actionEvent -> {
            try {
                switchScene(4);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }


    private void switchScene(int choice) throws ParseException {
        switch (choice) {
            case 1 -> MainApplication.switchController(new StartScreenPresenter());
            case 2 -> MainApplication.switchController(new RulesPresenter());
            case 3 -> MainApplication.switchController(new StatScreenPresenter());
            case 4 -> Platform.exit();
        }
    }
    @Override
    public View getView() {
        return view;
    }
}
