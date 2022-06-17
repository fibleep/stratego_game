package models;

public class Player {

    private String name;
    private String color;
    private Logger logger;

    public Player(String name) {

        this.name = name;
        this.logger=new Logger(name,true);
    }

    public Logger getLogger() {
        return logger;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}

