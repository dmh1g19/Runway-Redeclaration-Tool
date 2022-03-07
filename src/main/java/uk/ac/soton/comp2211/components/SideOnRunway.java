package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class SideOnRunway extends Canvas {

    /**
     * Draw the view for side-on runway here -
     */
    private Runway runway;
    private ObstacleOnRunway obstacle;
    private double width;
    private double height;
    private String direction;
    private double obPos;
    private double obLen;
    private double obHeight;
    private double TODALen;
    private double LDALen;
    private double sixtyLen;
    private double RESALen;
    private double ALSUp;
    private double ALSAcross;
    private GraphicsContext gc;

    public SideOnRunway(Runway runway, ObstacleOnRunway obstacle, double width, double height, String direction){
        this.runway = runway;
        this.obstacle = obstacle;
        this.width = width;
        this.height = height;
        this.direction = direction;

        setWidth(width);
        setHeight(height);

        obPos = (((double) obstacle.getPosition())/runway.getLength())*width; //relative position of object
        obLen = (((double)obstacle.getLength())/runway.getLength())*width; //relative length of object
        obHeight = (((double)obstacle.getHeight())/runway.getLength())*height; //relative height of object

        //relative sizes for TODA, 60m and the RESA are calculated.
        TODALen = ((double) runway.getTODA() / runway.getLength()) * width;
        LDALen = ((double) runway.getLDA() / runway.getLength()) * width;
        sixtyLen = (60.0/runway.getLength())*width;
        RESALen = (240.0/runway.getLength())*width;

        // length and height relative to view size are calculated.
        ALSUp = 2 * obstacle.getHeight();
        ALSAcross = 50 * ALSUp;
        ALSUp = (ALSUp / runway.getLength()) * height;
        ALSAcross = (ALSAcross /runway.getLength()) * height;
        gc = getGraphicsContext2D();

        representView();

        //add listener so that everytime runway or obstacle changed by user side on view updates.
    }
    //only using TODA right now - implement other two
    //need to add stopway and clearway to the view.
    //this represents view for plane taking off from left- add right towards an obstacle.
    //need to implement taking off away from an obstacle + landing
    public void representView() {
        //for now will represent plane as rectangle but implement drawImage later.

        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,0, width, height);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, (height - 20), width, 20); //fillRect(topLeftX, topLeftY, length, height)

        //the objects dimensions relative to the view size are calculated.
        gc.setFill(Color.RED);
        gc.fillRect((obPos - (obLen / 2)), (height -(obHeight + 20)), obLen, obHeight);
        //gc.fillRect(69.645, 174.29, 3.57, 5.71);
        landingView();
        //is RESA always 240m? - RESA can be ignored for longer distances.
    }

    public void takeOffView(){
        //taking off away from an obstruction
        if (((direction == "left") && (obPos > (width/2)))|| ((direction == "right") && (obPos < (width/2)))){
            double blastAllowance = (800.0/ runway.getLength())*width;
            gc.setLineWidth(3);
            if (direction == "right"){
                gc.setStroke(Color.ORANGE);
                gc.strokeLine(obPos+(obLen/2), (height - 30),obPos+(obLen/2) + blastAllowance ,(height - 30));
                gc.fillText("Blast Allowance: 800m",obPos+(obLen/2) + (blastAllowance/2),(height - 45) );

                gc.setStroke(Color.DARKGREEN);
                gc.strokeLine(obPos+(obLen/2) + blastAllowance, (height -30),obPos+(obLen/2) + blastAllowance+TODALen, (height - 30));
                gc.fillText(("TODA:" + runway.getTODA() + "m"), obPos+(obLen/2) + blastAllowance+(TODALen/2) ,(height - 45));

            }
            else{
                gc.setStroke(Color.ORANGE);
                gc.strokeLine(obPos-(obLen/2), (height - 30),obPos-(obLen/2) - blastAllowance ,(height - 30));
                gc.fillText("Blast Allowance: 800m",obPos-(obLen/2) - (blastAllowance/2),(height - 45) );

                gc.setStroke(Color.DARKGREEN);
                gc.strokeLine(obPos-(obLen/2) - blastAllowance, (height -30),obPos-(obLen/2) - blastAllowance-TODALen, (height - 30));
                gc.fillText(("TODA:" + runway.getTODA() + "m"), obPos-(obLen/2) - blastAllowance-(TODALen/2) ,(height - 45));
            }
        }
        //taking off towards an obstruction
        else{
            gc.setLineWidth(3);
            gc.setStroke(Color.DARKGREEN);


            if (direction == "right"){
                gc.strokeLine(1,(height - 30), (TODALen-1) ,(height - 30));
                gc.fillText(("TODA:" + runway.getTODA() + "m"), (TODALen / 2) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((TODALen+1),(height - 30),(TODALen + sixtyLen - 1),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((TODALen + sixtyLen + 1),(height - 30),(TODALen + sixtyLen +RESALen - 1),(height - 30));
                gc.fillText(("RESA: 240m"), (TODALen + sixtyLen +(RESALen/2)) ,(height - 45));

                gc.setLineWidth(1);
                gc.setStroke(Color.BLUE);
                gc.strokeLine(TODALen,(height - 20),TODALen + ALSAcross ,(height - 20) - ALSUp );
            }

            else{
                gc.strokeLine(width -1 ,(height - 30), (width - TODALen + 1) ,(height - 30));
                gc.fillText(("TODA:" + runway.getTODA() + "m"), (width - (TODALen / 2)) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((width - TODALen - 1),(height - 30),(width -(TODALen + sixtyLen - 1)),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((width -(TODALen + sixtyLen + 1)),(height - 30),(width -(TODALen + sixtyLen + RESALen - 1)),(height - 30));
                gc.fillText(("RESA: 240m"), (width -(TODALen + sixtyLen + (RESALen/2))) ,(height - 45));

                gc.setLineWidth(1);
                gc.setStroke(Color.BLUE);
                gc.strokeLine((width - TODALen),(height - 20),(width - TODALen) - ALSAcross,(height - 20) - ALSUp );
            }
        }
    }

    public void landingView(){
        //landing over an object
        if (((direction == "left") && (obPos > (width/2)))|| ((direction == "right") && (obPos < (width/2)))){
            gc.setLineWidth(3);
            gc.setStroke(Color.DARKGREEN);


            if (direction == "left"){
                gc.strokeLine(1,(height - 30), (LDALen-1) ,(height - 30));
                gc.fillText(("LDA:" + runway.getLDA() + "m"), (LDALen / 2) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((LDALen+1),(height - 30),(LDALen + sixtyLen - 1),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((LDALen + sixtyLen + 1),(height - 30),(LDALen + sixtyLen +RESALen - 1),(height - 30));
                gc.fillText(("RESA: 240m"), (LDALen + sixtyLen +(RESALen/2)) ,(height - 45));

                gc.setLineWidth(1);
                gc.setStroke(Color.BLUE);
                gc.strokeLine(LDALen,(height - 20),LDALen + ALSAcross ,(height - 20) - ALSUp );
            }

            else{
                gc.strokeLine(width -1 ,(height - 30), (width - LDALen + 1) ,(height - 30));
                gc.fillText(("LDA:" + runway.getLDA() + "m"), (width - (LDALen / 2)) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((width - LDALen - 1),(height - 30),(width -(LDALen + sixtyLen - 1)),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((width -(LDALen + sixtyLen + 1)),(height - 30),(width -(LDALen + sixtyLen + RESALen - 1)),(height - 30));
                gc.fillText(("RESA: 240m"), (width -(LDALen + sixtyLen + (RESALen/2))) ,(height - 45));

                gc.setLineWidth(1);
                gc.setStroke(Color.BLUE);
                gc.strokeLine((width - LDALen),(height - 20),(width - LDALen) - ALSAcross,(height - 20) - ALSUp );
            }
        }
        //landing towards an object
        else{

            if (direction == "right"){
                gc.strokeLine(1,(height - 30), (LDALen-1) ,(height - 30));
                gc.fillText(("LDA:" + runway.getLDA() + "m"), (LDALen / 2) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((LDALen+1),(height - 30),(LDALen + sixtyLen - 1),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((LDALen + sixtyLen + 1),(height - 30),(LDALen + sixtyLen +RESALen - 1),(height - 30));
                gc.fillText(("RESA: 240m"), (LDALen + sixtyLen +(RESALen/2)) ,(height - 45));
            }

            else{
                gc.strokeLine(width -1 ,(height - 30), (width - LDALen + 1) ,(height - 30));
                gc.fillText(("LDA:" + runway.getLDA() + "m"), (width - (LDALen / 2)) ,(height - 45));

                gc.setStroke(Color.YELLOW);
                gc.strokeLine((width - LDALen - 1),(height - 30),(width -(LDALen + sixtyLen - 1)),(height - 30));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((width -(LDALen + sixtyLen + 1)),(height - 30),(width -(LDALen + sixtyLen + RESALen - 1)),(height - 30));
                gc.fillText(("RESA: 240m"), (width -(LDALen + sixtyLen + (RESALen/2))) ,(height - 45));

            }
        }
    }
}
