
package Database;

import models.Player;
import models.Stats;

import java.sql.*;
import java.util.*;

import static java.lang.Integer.parseInt;

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
    private long startTime;

    public void dbInitialize(Player player) {
        try {
            this.startTime = System.currentTimeMillis();
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234"); //CONNECTION WITH THE DATABASE
            Statement statement = connection.createStatement();

            statement.execute("create table if not exists game_info (game_id integer primary key,player_name varchar(20), color varchar(5) NOT NULL constraint c_color check(color in ('Red','Blue')))");
            statement.execute("create sequence if not exists game_id_incr start 1");
            statement.execute("alter table game_info alter column game_id set default currval('game_id_incr')");

            statement.execute("create table if not exists turn_info (turn_number integer, game_id integer references game_info (game_id) on delete cascade, elapsed_time numeric not null, move varchar(500) not null, is_player boolean not null, score integer not null, primary key(turn_number,game_id))");
            statement.execute("create sequence if not exists turn_number_incr start 1");
            statement.execute("alter table turn_info alter column turn_number set default nextval('turn_number_incr')");
            statement.execute("alter sequence turn_number_incr restart with 1");

            statement.execute("create table if not exists end_stats (game_id integer references game_info (game_id) primary key, end_score integer, full_game_time numeric, is_won boolean)");
            statement.execute("alter table end_stats alter column game_id set default currval('game_id_incr')");
            statement.execute("insert into game_info values(nextval('game_id_incr'),'" + player.getName() + "','" + player.getColor() + "')");

            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void dbSaveTurn(StringBuilder move, Boolean isPlayer, int score) {

        // SAVING TURN INFO AFTER EVERY SINGLE TURN

        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            statement.execute("insert into turn_info values(default,(select max(game_id) from game_info),'"+(System.currentTimeMillis() - this.startTime)+"','"+move.toString()+"',"+isPlayer+","+score+")");
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbSaveGame(boolean isWon) {

        // SAVING INTO DB AFTER GAME IS FINISHED

        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            statement.execute("insert into end_stats values((select max(game_id) from game_info),(select sum(score) from turn_info group by game_id,is_player having is_player=true order by game_id desc fetch next row only),'" +(System.currentTimeMillis() - this.startTime) + "',"+isWon+")");
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public Stats dbGetStats() {

        // GET STATS FOM DB - EITHER LATEST GAME OR ALL GAMES

        Stats stats=new Stats();

        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
                List<Long> turnTimes = new ArrayList<>();
                Map<String,Integer> mapMoves = new HashMap<String,Integer>();
                ResultSet rs = statement.executeQuery("select elapsed_time,move,score from turn_info where is_player=true and game_id=(select max(game_id) from turn_info) order by turn_number");

                while (rs.next()) {
                    turnTimes.add(rs.getLong(1));
                    mapMoves.put(rs.getString(2),parseInt(rs.getString(3)));
                }

                stats.setTurnTimes(turnTimes);
                stats.setMapMoves(mapMoves);

                statement.close();
                connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
    public List<Integer> dbGetGlobalTurnCount(){
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("select max(sq.counts),min(sq.counts) from (select count(*) as counts from turn_info group by game_id) as sq;");
            List<Integer> result = new ArrayList<>();
            if(rs.next()){
                result.add(rs.getInt(1));
                result.add(rs.getInt(2));
            }
            statement.close();
            connection.close();
            return result;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int dbGetLocalTurnCount(){
        int result=0;
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) from turn_info group by game_id having game_id=(select max(game_id) from turn_info)");
            if(rs.next()) {
                result = rs.getInt(1);
            };
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    return result;
    }
    public List<Integer> dbGetWinLoseRatio(){
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("select lost,won from (select count(*) as lost from end_stats where is_won=false) as sq1,(select count(*) as won from end_stats where is_won=true) as sq2;");
            List<Integer> result = new ArrayList<>();
            if(rs.next()){
                result.add(rs.getInt(1));
                result.add(rs.getInt(2));
            }
            statement.close();
            connection.close();
            return result;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> dbGetLeaderboard(){
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql:SGDB",
                            "postgres", "Student_1234");
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("select player_name,end_score,full_game_time from game_info join end_stats on game_info.game_id=end_stats.game_id where end_stats.is_won=true order by end_score desc;");
            List<String> result = new ArrayList<>();
            int counter=0;
            while(rs.next()){
                counter++;
                result.add(counter+". NAME: "+rs.getString(1)+" SCORE: "+rs.getInt(2)+ " TOTAL TIME (SECONDS): " +rs.getInt(3)/1000);
            }
            statement.close();
            connection.close();
            return result;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
