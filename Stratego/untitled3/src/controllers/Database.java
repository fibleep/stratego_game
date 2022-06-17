package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
//    DATABASE NAME USED: SGDB
//    USERNAME: postgres
//    PASSWORD: Student_1234
//
//
//    To create a database, launch pgAdmin and login with username and password mentioned above.
//    Once logged in, click on “Servers” and then right click on “Databases”.
//    Select “Create” and then “Database…”
//    For the name of the database use “SGDB”
//    Click “Save”, you can close pgAdmin now.

    public void dbInitialize() {
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234"); //CONNECTION WITH THE DATABASE
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists game_info (game_id integer primary key, initial_board_state varchar(5000) not null,player_name varchar(20) NOT NULL, color varchar(5) NOT NULL constraint c_color check(color in ('Red','Blue')))");
            statement.execute("create sequence if not exists game_id_incr start 1");
            statement.execute("create table if not exists turn_info (turn_number integer, game_id integer references game_info (game_id) on delete cascade, elapsed_time timestamp not null, move varchar(50) not null, is_player boolean not null, score integer not null, primary key(turn_number,game_id))");
            statement.execute(" create sequence if not exists turn_number_incr start 1");
            statement.execute("alter sequence turn_number restart with 1");
            statement.execute("create table if not exists end_stats (game_id integer references game_info (game_id) primary key, final_score integer, total_time timestamp, pawns_lost integer, pawns_taken integer)");
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbSaveTurn(String time,String move,Boolean isPlayer,int score){

        // SAVING TURN INFO AFTER EVERY SINGLE TURN

        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            statement.execute("insert into turn_info values(nextval(turn_number_incr),currval(game_id_incr),to_timestamp("+time+",'MI:SS:MS'),"+move+",'True',0)");
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbSaveGame(int finalScore, String totalTime, int pawnsLost,int pawnsTaken, boolean isWon){

        // SAVING INTO DB AFTER GAME IS FINISHED

        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            // FINISH THIS PLZ
            statement.execute("insert into end_stats values(currval(game_id_incr))");
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
