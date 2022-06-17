package StatScreenView;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import util.View;

public class StatScreenView extends View {


    private Text turnLogText;
    private Text textLog;
    private Text moveStatsText;
    private Text leaderboardText;
    private Parent root;
    private Button log;
    private Text moveStats;
    private Button mainMenu;
    private Button switchButton;
    private BorderPane borderPane;
    private LineChart<String, Number> lineChart;
    private XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
    private XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();
    private PieChart pieChart;
    private ListView turnLogList;
    private ListView moveStatsList;
    private ListView leaderboard;
    private HBox hBox;
    private VBox vBox;
    private HBox hBox1;
    private VBox rightVBox;


    public StatScreenView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        this.pieChart=new PieChart();
        this.lineChart =new LineChart<>(new CategoryAxis(),new NumberAxis());

        turnLogText = new Text("Turn Log");
        moveStatsText=new Text("Move Stats");
        leaderboardText=new Text("Leaderboard");
        textLog= new Text();

        log = new Button("Log");
        moveStats = new Text();
        borderPane = new BorderPane();
        hBox = new HBox();
        vBox = new VBox();
        hBox1 = new HBox();
        rightVBox=new VBox();


    }

    private void layoutNodes() {
        turnLogText.setStyle("-fx-font: 25 arial;");
        moveStats.setStyle("-fx-font: 25 arial;");
        leaderboardText.setStyle("-fx-font: 25 arial;");

        mainMenu = new Button("MAIN MENU");
        switchButton = new Button("SWITCH LOCAL/GLOBAL");
        turnLogList = new ListView();
        moveStatsList=new ListView();
        leaderboard =new ListView();

        turnLogList.setPrefWidth(600);
        hBox.getChildren().addAll(mainMenu,switchButton);
        hBox.setAlignment(Pos.CENTER);

        mainMenu.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        mainMenu.setMinSize(300,100);

        switchButton.setStyle("-fx-background-color: #1F2833;-fx-border-color: #0b0c10; -fx-border-width: 2px;-fx-font-size: 2em;-fx-text-fill: #C5C6C7");
        switchButton.setMinSize(300,100);

        vBox.getChildren().addAll(turnLogText, turnLogList,moveStatsText,moveStatsList);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(lineChart,leaderboardText,leaderboard);

        hBox1.getChildren().addAll(vBox,pieChart,moveStats, rightVBox);
        hBox1.setAlignment(Pos.CENTER);

        borderPane.setCenter(hBox1);
        borderPane.setBottom(hBox);
        root = borderPane;
    }

    public Button getMainMenu() {
        return mainMenu;
    }


    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public XYChart.Series<String, Number> getSeries1() {
        return series1;
    }

    public XYChart.Series<String, Number> getSeries2() {
        return series2;
    }

    public Text getMoveStats() {
        return moveStats;
    }

    public Text getTurnLogText() {
        return turnLogText;
    }

    public Text getTextLog() {
        return textLog;
    }

    public Button getLog() {
        return log;
    }

    public ListView getTurnLogList() {
        return turnLogList;
    }

    public Button getSwitchButton() {
        return switchButton;
    }


    public ListView getMoveStatsList() {
        return moveStatsList;
    }

    public ListView getLeaderboard() {
        return leaderboard;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
