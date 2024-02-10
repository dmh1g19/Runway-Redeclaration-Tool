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

public class DrawBasedOnDirection {
    
    private Color TODA_COLOR = Color.rgb(255,145,255);
    private Color TORA_COLOR = Color.rgb(95,185,255);
    private Color ASDA_COLOR = Color.rgb(255,150,50);

    private boolean reflected;
    private double height, width;
    private double closeStopwayLength, farStopwayLength, closeClearwayLength, farClearwayLength;
    private Direction dToObstacle;
    private RedeclaredRunway runway;
    private double ALSUp, scaledBlast, scaledRESA, scaledDTL;
    private GraphicsContext gc;
    private double obPos, RESALen, sixtyLen, TODALen, ALSAcross, LDALen, obLen, ASDALen, TORALen, blastAllowance, blastProtection;

    public DrawBasedOnDirection(boolean reflected, double height, double closeStopwayLength, double farStopwayLength, 
                                double closeClearwayLength, double farClearwayLength, Direction dToObstacle, 
                                RedeclaredRunway runway, double ALSUp, double scaledBlast, 
                                double scaledRESA, double scaledDTL, GraphicsContext gc, 
                                double obPos, double RESALen, double sixtyLen, double TODALen, double TORALen, 
                                double ASDALen, double LDALen, double ALSAcross, double width, double obLen) {

        this.obLen = obLen;
        this.reflected = reflected;
        this.height = height;
        this.closeStopwayLength = closeStopwayLength;
        this.farStopwayLength = farStopwayLength;
        this.closeClearwayLength = closeClearwayLength;
        this.farClearwayLength = farClearwayLength;
        this.dToObstacle = dToObstacle;
        this.runway = runway;
        this.ALSUp = ALSUp;
        this.scaledBlast = scaledBlast;
        this.scaledRESA = scaledRESA;
        this.scaledDTL = scaledDTL;
        this.gc = gc;
        this.obPos = obPos;
        this.RESALen = RESALen;
        this.sixtyLen = sixtyLen;
        this.ASDALen = ASDALen;
        this.TODALen = TODALen;
        this.TORALen = TORALen;
        this.ALSAcross = ALSAcross;
        this.LDALen = LDALen;
        this.width = width;
    }

    public void takeOffView() {
        if (this.dToObstacle == Direction.AWAYOVER) {
            drawTakeOffAway();
        } else {
            drawTakeOffTowards();
        }
    }

    public void drawTakeOffTowards() {
        double startX, endX, textPositionX;
        final double lineY = this.height - 50; 
    
        this.gc.setFill(Color.BLACK);
        this.gc.setLineWidth(3);
    
        startX = this.closeStopwayLength + this.closeClearwayLength;
        
        endX = startX + this.TODALen;
        textPositionX = (startX + endX) / 2;
        drawLineWithText(startX, endX, lineY, TODA_COLOR, "TODA: " + this.runway.getRunway().getTODA() + "m", textPositionX, this.reflected);
    
        endX = startX + this.TORALen; 
        drawLineWithText(startX, endX, lineY - 30, TORA_COLOR, "TORA: " + this.runway.getRunway().getTORA() + "m", textPositionX, this.reflected);
   
        endX = startX + this.ASDALen; 
        drawLineWithText(startX, endX, lineY - 60, ASDA_COLOR, "ASDA: " + this.runway.getRunway().getASDA() + "m", textPositionX, this.reflected);
    
        startX = endX;
        endX = startX + this.sixtyLen + this.RESALen;
        drawLineWithText(startX, endX, lineY, Color.ORANGE, "RESA: 240m", (startX + endX) / 2, this.reflected);
    
        startX = this.TODALen;
        endX = startX + this.ALSAcross;

        this.gc.setStroke(Color.GREEN);
        this.gc.setLineWidth(2);
        this.gc.strokeLine(startX, lineY - 20, endX, lineY - 20 - this.ALSUp);
    }

    public void drawTakeOffAway() {
        double startX, endX, textPositionX;
        
        final double lineY = this.height - 50;

        this.gc.setFill(Color.BLACK);
        this.gc.setLineWidth(3);
        this.gc.setStroke(Color.DARKGREEN);

        double blastAllowance = Math.max(this.scaledBlast, this.scaledRESA);
        startX = this.obPos + (this.obLen / 2);
        endX = startX + blastAllowance;
        textPositionX = startX + (blastAllowance / 2);
        drawLineWithText(startX, endX, lineY, Color.ORANGE, "Blast Allowance / RESA: " + this.blastProtection, textPositionX, this.reflected);

        startX = endX; 
        endX = startX + this.TODALen;
        textPositionX = startX + (this.TODALen / 2);

        drawLineWithText(startX, endX, lineY, Color.GREEN, "TODA: " + this.runway.getRunway().getTODA() + "m", textPositionX, this.reflected);
        drawLineWithText(startX, startX + this.TORALen, lineY - 30, Color.BLUE, "TORA: " + this.runway.getRunway().getTORA() + "m", textPositionX, this.reflected);
        drawLineWithText(startX, startX + this.ASDALen, lineY - 60, Color.PURPLE, "ASDA: " + this.runway.getRunway().getASDA() + "m", textPositionX, this.reflected);
    }
    public void landingView() {
        if (this.dToObstacle == Direction.AWAYOVER) {
            this.drawLandingOver();
        } else {
            this.drawLandingTowards();
        }
    }

    public void drawLandingOver() {
        double startX, endX, textPositionX;
        final double lineY = this.height - 50; // Common y-coordinate for lines

        this.gc.setLineWidth(3);

        // LDA Line
        startX = this.width - this.farStopwayLength - this.farClearwayLength - this.LDALen;
        endX = this.width - this.farStopwayLength - this.farClearwayLength;
        textPositionX = (startX + endX) / 2; // Center text between startX and endX
        this.drawLineWithText(startX, endX, lineY, Color.DARKGREEN, "LDA: " + this.runway.getRunway().getLDA() + "m", textPositionX, this.reflected);

        // Drawing RESA line considering the landing towards an obstacle
        startX = this.width - this.LDALen - this.farClearwayLength - this.farStopwayLength;
        endX = startX - this.sixtyLen - this.RESALen; // Adjust for RESA length
        this.drawLineWithText(startX, endX, lineY, Color.ORANGE, "RESA: 240m", (startX + endX) / 2, this.reflected);

        // ALS Line
        this.gc.setLineWidth(2);
        this.gc.setStroke(Color.GREEN);
        startX = this.width - this.LDALen; // ALS starts from the end of LDA
        endX = startX - this.ALSAcross; // Extend for ALS length, adjust direction for landing
        this.gc.strokeLine(startX, lineY - 20, endX, lineY - 20 - this.ALSUp); // Drawing ALS line, adjust y-coordinates for visual clarity
    }

    public void drawLandingTowards() {
        double offset = this.closeStopwayLength + this.closeClearwayLength + this.scaledDTL;
        this.gc.strokeLine(offset, this.height - 50, this.LDALen + offset, this.height - 50);
        this.writeText("LDA: " + this.runway.getRunway().getLDA() + "m", this.LDALen / 2 + offset, this.height - 65, this.reflected);

        this.gc.setStroke(Color.YELLOW);
        this.gc.strokeLine(this.LDALen + offset, this.height - 50, this.LDALen + this.sixtyLen + offset, this.height - 50);

        this.gc.setStroke(Color.ORANGE);
        this.gc.strokeLine(this.LDALen + this.sixtyLen + offset, this.height - 50, this.LDALen + this.sixtyLen + this.RESALen + offset, this.height - 50);
        this.writeText("RESA: 240m", this.LDALen + this.sixtyLen / 2 + offset, this.height - 65, this.reflected);
    }

    private void writeText(String s, double x, double y, boolean reflected) {
        if (reflected) {
            this.reflectText(s, x, y);
        } else {
            this.gc.fillText(s, x, y);
        }
    }

    private void reflectText(String s, double x, double y) {
        this.gc.save();
        this.gc.transform(new Affine(new Scale(-1, 1, x, y)));
        this.gc.fillText(s, -x, y); // Correct reflection position
        this.gc.restore();
    }

    private void drawLineWithText(double startX, double endX, double y, Color color, String text, double textPositionX, boolean reflected) {
        this.gc.setStroke(color);
        this.gc.setLineWidth(3);
        this.gc.strokeLine(startX, y, endX, y);
        this.writeText(text, textPositionX, y - 15, reflected);
    }
}