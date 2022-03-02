package uk.ac.soton.comp2211.airport;

public class Obstacle {
    protected  String name;
    protected  int height;
    protected  int length;
    //protected Image image (the png file or something idk)

    public Obstacle(String name, int height, int length) {
        this.name = name;
        this.height = height;
        this.length = length;
    }
}

