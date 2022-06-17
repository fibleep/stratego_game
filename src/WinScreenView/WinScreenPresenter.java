package WinScreenView;

import ChooseTeamView.ChooseTeamPresenter;
import MenuScreenView.MenuScreenPresenter;
import StatScreenView.StatScreenPresenter;
import models.Player;
import starter.MainApplication;
import util.Controller;
import util.View;

import java.text.ParseException;


public class WinScreenPresenter extends Controller {


    private WinScreenView view;

    public WinScreenPresenter(boolean hasWon, Player player) {
        this.view = new WinScreenView();

        if (!hasWon) {
            view.getText().setText("You Lost");
        }

        view.getExit().setOnAction(actionEvent -> MainApplication.switchController(new MenuScreenPresenter()));
        view.getStats().setOnAction(actionEvent -> {
            try {
                MainApplication.switchController(new StatScreenPresenter());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        view.getStartAgain().setOnAction(actionEvent -> MainApplication.switchController(new ChooseTeamPresenter(player)));

    }

    @Override
    public View getView() {
        return view;
    }


}
