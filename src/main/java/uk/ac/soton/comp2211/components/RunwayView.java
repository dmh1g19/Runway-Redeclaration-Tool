package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.State;

public abstract class RunwayView extends Canvas {
    protected int blastProtection =0;

    private RedeclaredRunway runway;

    final Color TODA_COLOR = Color.rgb(255,145,255);
    final Color TORA_COLOR = Color.rgb(95,185,255);
    final Color LDA_COLOR = Color.rgb(255,255,0);
    final Color RESA_COLOR = Color.rgb(255,255,255);
    final Color ASDA_COLOR = Color.rgb(255,150,50);

    RunwayView (double width, double height){
        setWidth(width);
        setHeight(height);
    }

    public abstract void draw(RedeclaredRunway runway, RedeclaredRunway oppRunway,State state);

    public int getBlastProtection() {
        return blastProtection;
    }

    public void setBlastProtection(int blastProtection) {
        this.blastProtection = blastProtection;
    }

}
