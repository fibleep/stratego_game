package ChooseTeamView;

import SetupView.SetupPresenter;
import models.Player;
import starter.MainApplication;
import util.Controller;
import util.View;

public class ChooseTeamPresenter extends Controller {

    private ChooseTeamView view;

    public ChooseTeamPresenter(Player player) {
        this.view = new ChooseTeamView();

        view.getBlueButton().setOnAction(actionEvent -> switchScene(("Blue"),player));
        view.getRedButton().setOnAction(actionEvent -> switchScene(("Red"),player));
    }

    private void switchScene(String color, Player player) {
        player.setColor(color);
        MainApplication.switchController(new SetupPresenter(player));
    }

    @Override
    public View getView() {
        return view;
    }
}
