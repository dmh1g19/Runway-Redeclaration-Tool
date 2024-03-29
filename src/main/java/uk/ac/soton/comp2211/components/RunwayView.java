package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.State;

public abstract class RunwayView extends Canvas {
    protected int blastProtection = 0;

    private RedeclaredRunway runway;

    Color TODA_COLOR = Color.rgb(255,145,255);
    Color TORA_COLOR = Color.rgb(95,185,255);
    Color LDA_COLOR = Color.rgb(255,255,0);
    Color RESA_COLOR = Color.rgb(255,255,255);
    Color ASDA_COLOR = Color.rgb(255,150,50);

    private double MIN_SCALE = 0.2;
    private double MAX_SCALE = 3;

    private double scrollStartX = 0;
    private double scrollStartY = 0;
    private double lastScrollEndY = 0;
    private double lastScrollEndX = 0;

    private double deltaX = 0;
    private double deltaY = 0;


    double scale = 0.9;
    double x = 0;
    double y = 0;

    RunwayView (double width, double height){
        setWidth(width);
        setHeight(height);
    }

    public abstract void redraw();
    public abstract void draw(RedeclaredRunway runway, RedeclaredRunway oppRunway,State state);

    public int getBlastProtection() {
        return blastProtection;
    }

    public void setBlastProtection(int blastProtection) {
        this.blastProtection = blastProtection;
    }

    public void zoom(double zoomDelta)
    {
        scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale+zoomDelta));
        redraw();
    }

    public void scroll(double newX, double newY)
    {
        deltaX = scrollStartX-newX;
        deltaY = scrollStartY-newY;

        x = lastScrollEndX - deltaX/scale;
        y = lastScrollEndY - deltaY/scale;

        redraw();
    }

    public void enterScroll(double newX, double newY)
    {
        scrollStartX = newX;
        scrollStartY = newY;
    }

    public void exitScroll(double newX, double newY)
    {
        lastScrollEndX=x;
        lastScrollEndY=y;
    }

    public Color getTODAColor() {
        return this.TODA_COLOR;
    }

    public Color getTORAColor() {
        return this.TORA_COLOR;
    }

    public Color getLDAColor() {
        return this.LDA_COLOR;
    }

    public Color getRESAColor() {
        return this.RESA_COLOR;
    }

    public Color getASDAColor() {
        return this.ASDA_COLOR;
    }
}
