package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;

public class ObstacleDrawer {

    private static final double OBSTACLE_SCALING_FACTOR = 10.0;
    private static final double LANDING_STRIP_HEIGHT = 20;
    private static final double DIRECTION_ARROW_BASE = 60;
    private static final double DIRECTION_ARROW_HEIGHT = 20;
    private static final double DIRECTION_ARROW_WIDTH = 5;

    private final GraphicsContext gc;
    private final double width;
    private final double height;
    private final boolean reflected;
    private final double obPos;
    private final double obLen;
    private final double obHeight;

    public ObstacleDrawer(GraphicsContext gc, double width, double height, boolean reflected, double obPos, double obLen, double obHeight) {
        this.gc = gc;
        this.width = width;
        this.height = height;
        this.reflected = reflected;
        this.obPos = obPos;
        this.obLen = obLen;
        this.obHeight = obHeight;
    }

    public void drawObstacle() {
        gc.setFill(Color.RED);
        gc.fillRect((obPos - (obLen / 2)), (height - ((obHeight * OBSTACLE_SCALING_FACTOR) + LANDING_STRIP_HEIGHT)), obLen, obHeight * OBSTACLE_SCALING_FACTOR);
        gc.setFill(Color.BLACK);
        gc.strokeLine(obPos, (height - DIRECTION_ARROW_BASE), obPos, (height - (DIRECTION_ARROW_BASE + DIRECTION_ARROW_HEIGHT)));
        double[] xPoints = {obPos, obPos + DIRECTION_ARROW_WIDTH, obPos - DIRECTION_ARROW_WIDTH};
        double[] yPoints = {(height - (DIRECTION_ARROW_BASE - 2)), (height - (DIRECTION_ARROW_BASE + DIRECTION_ARROW_HEIGHT)), (height - (DIRECTION_ARROW_BASE + DIRECTION_ARROW_HEIGHT))};
        gc.fillPolygon(xPoints, yPoints, 3);

        if (reflected) {
            gc.save();
            gc.transform(new Affine(new Scale(-1, 1, obPos, (height - 85))));
            gc.fillText("Obstacle", obPos - 25, (height - 85));
            gc.restore();
        } else {
            gc.fillText("Obstacle", obPos - 25, (height - 85));
        }
    }
}
