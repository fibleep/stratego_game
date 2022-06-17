package StartScreenView;


import ChooseTeamView.ChooseTeamPresenter;
import models.Player;
import starter.MainApplication;
import util.Controller;
import util.View;

public class StartScreenPresenter extends Controller {

    private StartScreenView view;

    public StartScreenPresenter() {
        this.view = new StartScreenView();
        view.getButton().setOnAction(actionEvent -> switchScene(view.getTextField().getText()));
    }


    private void switchScene(String name) {
        Player player = new Player(name);
        MainApplication.switchController(new ChooseTeamPresenter(player));
    }


    @Override
    public View getView() {
        return view;
    }
}
