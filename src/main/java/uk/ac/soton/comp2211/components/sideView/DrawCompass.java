package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import uk.ac.soton.comp2211.airport.Direction;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.State;

public class DrawCompass {

    private double height, width;
    private GraphicsContext gc;
    private boolean reflected;
    private RedeclaredRunway runway;

    public DrawCompass(RedeclaredRunway runway, GraphicsContext gc, double height, double width, boolean reflected) {
        this.runway = runway;
        this.gc = gc;
        this.height = height;
        this.width = width;
        this.reflected = reflected;
    }

    public void drawCompass() {
        final double compassX = width - 100; // Top right position
        final double compassY = height / 10;
        final double compassSize = 50;
        final double needleStartY = compassY + 25;
        final double needleEndY = compassY + 7;
        final double textOffset = 25; // Offset for text positioning

        drawCompassBase(compassX, compassY, compassSize);
        drawCompassNeedle(compassX + compassSize / 2, needleStartY, needleEndY);
        drawCompassLabels(compassX, compassY, textOffset);
    }

    private void drawCompassBase(double x, double y, double size) {
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, size, size);
        gc.strokeOval(x, y, size, size);
    }

    private void drawCompassNeedle(double x, double startY, double endY) {
        double bearing = reflected ? runway.getRunway().getBearing() + 180 : runway.getRunway().getBearing();
        gc.save();
        gc.transform(new Affine(new Rotate(bearing, x, startY)));
        gc.setStroke(Color.BLUE);
        gc.strokeLine(x, startY, x, endY);
        gc.restore();
    }

    private void drawCompassLabels(double x, double y, double offset) {
        String bearingText = runway.getRunway().getBearing() + "°";
        gc.setFill(Color.RED);
        // Adjustments for reflected text
        if (reflected) {
            gc.save();
            gc.transform(new Affine(new Scale(-1, 1, x + offset, y)));
            gc.fillText("N", -(x + offset + 4), y - offset);
            gc.fillText(bearingText, -(x + offset - 25), y + offset + 5);
            gc.fillText("Bearing", -(x + offset - 25), y + offset - 5);
            gc.restore();
        } else {
            gc.fillText("N", x + offset - 4, y - offset);
            gc.fillText(bearingText, x + offset - 25, y + offset + 5);
            gc.fillText("Bearing", x + offset - 25, y + offset - 5);
        }
        drawAspectRatioInfo();
    }

    private void drawAspectRatioInfo() {
        String aspectRatioText = "Width : Height = 1 : 10";
        if (reflected) {
            gc.save();
            gc.transform(new Affine(new Scale(-1, 1, 70, 50)));
            gc.fillText(aspectRatioText, -70, 15); // Adjust text position for reflection
            gc.restore();
        } else {
            gc.fillText(aspectRatioText, 0, 15);
        }
    }

}