package uk.ac.soton.comp2211.airport;

public class Obstacle {
    protected String name;
    protected int height;
    protected int length;
    //protected Image image (the png file or something idk)

    public Obstacle() { }

    public Obstacle(String name, int height, int length) {
        this.name = name;
        this.height = height;
        this.length = length;
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
}

