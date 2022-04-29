package uk.ac.soton.comp2211.airport;

public class Obstacle {
    protected String name;
    protected int height;
    protected int length;
    protected int width;
    //protected Image image (the png file or something idk)

    public Obstacle() { }

    public Obstacle(String name, int height, int length, int width) {
        this.name = name;
        this.height = height;
        this.length = length;
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
}

