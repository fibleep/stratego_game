package StatScreenView;

import Database.Database;
import MenuScreenView.MenuScreenPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import models.Stats;
import starter.MainApplication;
import util.Controller;
import util.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

public class StatScreenPresenter extends Controller {

    private StatScreenView view;
    private String moveStats;
    private final Database SGDB = new Database();
    private Stats ownStats;
    private List<Stats> allStats;
    private boolean isLocal = true;


    public StatScreenPresenter() throws ParseException {
        this.view = new StatScreenView();
        this.ownStats = SGDB.dbGetStats();
        view.getLineChart().getData().addAll(view.getSeries1(),view.getSeries2());
        view.getMainMenu().setOnAction(actionEvent -> MainApplication.switchController(new MenuScreenPresenter()));
        view.getSwitchButton().setOnAction(actionEvent -> {
            isLocal = !isLocal;
            updateData();
        });
        updateLineChart();
        updateData();
        showLeaderboard();
    }

    private void showLog() {
        String turnlog = "";
        view.getTurnLogList().getItems().clear();
        view.getMoveStatsList().getItems().clear();

        for (Map.Entry<String, Integer> entry : ownStats.getMapMoves().entrySet()) {
            view.getTurnLogList().getItems().add(turnlog + entry.getKey());
        }

        view.getTextLog().setText(turnlog);
        view.getMoveStatsList().getItems().clear();
        if (isLocal) {
            view.getMoveStatsList().getItems().addAll(getMoveStats(), "Turns taken by the player: " + SGDB.dbGetLocalTurnCount());
        } else {
            view.getMoveStatsList().getItems().addAll("Max Turn Count: " + SGDB.dbGetGlobalTurnCount().get(0), "Min Turn Count: " + SGDB.dbGetGlobalTurnCount().get(1));
        }

    }

    private void updateData() {
        showLog();
        updatePieChart();
    }

    private void showLeaderboard() {
            List<String> leaderboard = SGDB.dbGetLeaderboard();
            for (int i = 0; i < leaderboard.size(); i++) {
                view.getLeaderboard().getItems().add(leaderboard.get(i));
            }
    }


    private void updatePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> temp = new TreeMap<>();
        if(isLocal) {
            for (String key : ownStats.getMapMoves().keySet()) {
                if (temp.containsKey(String.valueOf(ownStats.getMapMoves().get(key)))) {
                    temp.put(String.valueOf(ownStats.getMapMoves().get(key)), temp.get(String.valueOf(ownStats.getMapMoves().get(key))) + 1);
                } else {
                    temp.put(String.valueOf(ownStats.getMapMoves().get(key)), 1);
                }
            }
            for (String key : temp.keySet()) {
                pieChartData.add(new PieChart.Data(translateScoreToString(parseInt(key)), temp.get(key)));
            }
            view.getPieChart().setData(pieChartData);
            view.getPieChart().setTitle("Percentage Of Moves With Certain Scores");
            view.getLineChart().setTitle("Time Per Turn");
        }
        else{
            view.getPieChart().setTitle("Global Win/Lose Ratio");
            pieChartData.add(new PieChart.Data("Lost", SGDB.dbGetWinLoseRatio().get(0)));
            pieChartData.add(new PieChart.Data("Won", SGDB.dbGetWinLoseRatio().get(1)));
            view.getPieChart().setData(pieChartData);
        }

    }

    private String translateScoreToString(int score) {
        String result = "";
        switch (score) {
            case 0 -> result = "Deaths";
            case 2 -> result = "Moves to Empty";
            case 5 -> result = "Trades";
            case 10 -> result = "Kills";
            case 15 -> result = "Bomb Defusals";
            case 200 -> result = "Winning Move";
        }
        return result;
    }

    private String getMoveStats() {

        long maxTime = Collections.max(getFlatTurnTimes());
        long sum = 0;
        for (int i = 0; i < getFlatTurnTimes().size(); i++) {
            sum = sum + getFlatTurnTimes().get(i);
        }
        long avgTime = sum / getFlatTurnTimes().size();
        return "MAX TIME: " + toTimestamp(maxTime) + "\nAVG TIME: " + toTimestamp(avgTime);
    }

    private List<Long> getFlatTurnTimes() {
        List<Long> result = new ArrayList<Long>();
            for (int i = 0; i < ownStats.getTurnTimes().size(); i++) {
                if (i == 0) {
                    result.add(ownStats.getTurnTimes().get(i));
                } else {
                    result.add(ownStats.getTurnTimes().get(i) - ownStats.getTurnTimes().get(i - 1));
                }
            }
        return result;
    }

    private String toTimestamp(long currTime) {
        DateFormat df = new SimpleDateFormat("mm:ss:SSS");
        return df.format(currTime);
    }

    private void updateLineChart() {
        view.getSeries1().setName("Movement Time");
        int counter = 0;
        for (Long key : ownStats.getTurnTimes()) {
            view.getSeries1().getData().add(new XYChart.Data<>(String.valueOf(counter), key/1000));
            counter++;
        }
        view.getSeries2().setName("Movement Time - FLAT");
        counter = 0;
        for (Long key : getFlatTurnTimes()) {
            view.getSeries2().getData().add(new XYChart.Data<>(String.valueOf(counter), key/1000));
            counter++;
        }
    }

    @Override
    public View getView() {
        return view;
    }
}