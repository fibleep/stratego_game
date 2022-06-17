package RulesView;

import MenuScreenView.MenuScreenPresenter;
import starter.MainApplication;
import util.Controller;
import util.View;

public class RulesPresenter extends Controller {

    private RulesView view;

    public RulesPresenter() {
        this.view = new RulesView();
        view.getExit().setOnAction(actionEvent -> MainApplication.switchController(new MenuScreenPresenter()));
        view.getGoal().setOnAction(actionEvent -> view.getText5().setText("The goal of the game is to capture the enemy FLAG."));
        view.getAttack().setOnAction(actionEvent -> view.getText5().setText("→ If two pieces of opposite color attack each other the game compares their ranks.\n" +
                "→ If the pieces are of the same rank, they both die.\n" +
                "→ Higher rank beats lower rank.\n" +
                "→ EXCEPTION:  BOMB can only be defused by MINER.\n" +
                "→ EXCEPTION: if SPY attacks, it can capt"));
        view.getMovement().setOnAction(actionEvent -> view.getText5().setText("→ The pieces can only move one space either forward / backward / sideways.\n" +
                "→ SPECIAL EXCEPTION: SCOUT can move any number of spaces.\n" +
                "→ You can’t move back and forth with one piece three times in a row.\n" +
                "→ You can’t jump over LAKES / your pieces / enemy pieces."));
        view.getBoardAndSetup().setOnAction(actionEvent -> view.getText5().setText(" 10x10 GRID\n" +
                "→ 8 LAKES\n" +
                "→ 80 PIECES\n" +
                "→ 40 PLAYER PIECES\n" +
                "→ 1 FLAG\n" +
                "→ 6 BOMBS\n" +
                "→ 33 MOVABLE PIECES \n" +
                "→ 1 MARSHAL → RANK 10\n" +
                "→ 1 GENERAL → RANK 9 \n" +
                "→ 2 COLONELS → RANK 8 \n" +
                "→ 3 MAJORS → RANK 7\n" +
                "→ 4 CAPTAINS → RANK 6 \n" +
                "→ 4 LIEUTENANTS → RANK 5 \n" +
                "→ 4 SERGEANTS → RANK 4\n" +
                "→ 5 MINERS → RANK 3\n" +
                "→ 8 SCOUTS → RANK 2 \n" +
                "→ 1 SPY  → RANK 1\n\n" +
                "The game is played by alternating turns between the players, >>RED<< always starts first.\n" +
                "During the SETUP phase, you can click on a pawn and then choose its position on the board."));
        view.getHints().setOnAction(actionEvent -> view.getText5().setText("→ When placing your pieces, place your FLAG somewhere in the back, surrounded by BOMBS.\n" +
                "→ If you find a BOMB it is likely a FLAG is nearby\n" +
                "→ protect your MINERS, if the enemy’s flag is surrounded by BOMBS you need MINERS to get to it.\n" +
                "→ use your SCOUTS to reveal enemy positions.\n" +
                "→ Be wary of the pieces that haven’t moved yet!\n"));

    }

    @Override
    public View getView() {
        return view;
    }
}
