package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.State;
import uk.ac.soton.comp2211.listeners.RunwayUpdatedListener;
import uk.ac.soton.comp2211.scene.CalculationsScene;

public class SideOnRunway extends RunwayView {

    /**
     * Draw the view for side-on runway here -
     */
    private RedeclaredRunway runway;
    private ObstacleOnRunway obstacle;
    private double width;
    private double height;
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

    public SideOnRunway(double width, double height){
        super(width,height );



        representView(runway);

    }

    //need to add stopway and clearway to the view.
    public void representView(RedeclaredRunway runway1) {

        //Moved from constructor
        this.runway = runway1;
        this.obstacle = runway.getObstacle();

        obPos = (((double) obstacle.getPosition())/runway.getRunway().getLength())*width; //relative position of object
        obLen = (((double)obstacle.getLength())/runway.getRunway().getLength())*width; //relative length of object
        obHeight = (((double)obstacle.getHeight())/runway.getRunway().getLength())*height; //relative height of object

        //relative sizes for TODA, 60m and the RESA are calculated.
        TODALen = ((double) runway.getRunway().getTODA() / runway.getRunway().getLength()) * width;
        LDALen = ((double) runway.getRunway().getLDA() / runway.getRunway().getLength()) * width;
        sixtyLen = (60.0/runway.getRunway().getLength())*width;
        RESALen = (240.0/runway.getRunway().getLength())*width;

        // length and height of ALS slope relative to view size are calculated.
        ALSUp = 4 * obstacle.getHeight();
        ALSAcross = 50 * ALSUp;
        ALSUp = (ALSUp / runway.getRunway().getLength()) * height * 10;
        ALSAcross = (ALSAcross /runway.getRunway().getLength()) * height;
        gc = getGraphicsContext2D();

        //Original Function

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0, width, height);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, (height - 20), width, 20);

        gc.fillText("Width : Height = 1 : 10",0,15);

        //the obstructions dimensions relative to the view size are calculated.
        gc.setFill(Color.RED);
        gc.fillRect((obPos - (obLen / 2)), (height -((obHeight*10) + 20)), obLen, obHeight*10);
        gc.setFill(Color.BLACK);
        gc.strokeLine(obPos,(height-60), obPos, (height - 80));
        double[] xPoints = {obPos,obPos+5,obPos-5};
        double[] yPoints = {(height - 58),(height - 65), (height - 65) };
        gc.fillPolygon(xPoints ,yPoints , 3);
        gc.fillText("Obstacle",obPos - 25, (height - 85));

        //A compass in the top right of the screen that shows the bearing of the runway
        gc.setFill(Color.WHITE);
        gc.fillOval(width - 100, height/10 , 50,50);
        gc.strokeOval(width - 100, height/10 , 50,50);
        gc.setStroke(Color.RED);
        gc.save();
        gc.transform(new Affine(new Rotate(runway.getRunway().getBearing()*10,(width - 75),(height/10 +25))));
        gc.strokeLine(width - 75, height/10 +25, width - 75, height/10 + 7  );
        gc.restore();
        gc.setFill(Color.BLACK);
        gc.fillText("Bearing" ,width - 45,height/10 +30 );
        gc.fillText(runway.getRunway().getBearing()+"",width - 45,height/10 + 20 );

        //right now just shows take off view but buttons will be installed to switch between the two
        takeOffView();
    }

    //shows planes taking off away from and towards and obstruction.
    public void takeOffView(){
        gc.setLineWidth(3);
        gc.setStroke(Color.DARKGREEN);
        //taking off away from an obstruction
        if (obPos < (width/2)){
            double blastAllowance = (300.0/ runway.getRunway().getLength())*width;
            gc.setLineWidth(3);
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(obPos+(obLen/2), (height - 50),obPos+(obLen/2) + blastAllowance ,(height - 50));
            gc.fillText("Blast Allowance: 800m",obPos+(obLen/2) + (blastAllowance/2),(height - 65) );

            gc.setStroke(Color.DARKGREEN);
            gc.strokeLine(obPos+(obLen/2) + blastAllowance, (height -50),obPos+(obLen/2) + blastAllowance+TODALen, (height - 50));
            gc.fillText(("TODA:" + runway.getRunway().getTODA() + "m"), obPos+(obLen/2) + blastAllowance+(TODALen/2) ,(height - 65));
        }
        //taking off towards an obstruction
        else{
            gc.strokeLine(1,(height - 50), (TODALen-1) ,(height - 50));
            gc.fillText(("TODA:" + runway.getRunway().getTODA() + "m"), (TODALen / 2) ,(height - 65));

            //if the RESA is less than the distance of runway taken away by the obstacle then that distance is used instead of RESA
            if ((( obstacle.getPosition() - (obstacle.getLength()/2)) - runway.getRunway().getTODA()  ) > 300){
                gc.setStroke(Color.ORANGE);
                gc.strokeLine( (obPos-(obLen/2)),
                        (height - 50),
                        TODALen ,
                        (height - 50));
            }
            else{
                gc.setStroke(Color.YELLOW);
                gc.strokeLine((TODALen+1),(height - 50),(TODALen + sixtyLen - 1),(height - 50));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((TODALen + sixtyLen + 1),(height - 50),(TODALen + sixtyLen +RESALen - 1),(height - 50));
                gc.fillText(("RESA: 240m"), (TODALen + sixtyLen +(RESALen/2)) ,(height - 65));
            }

            gc.setLineWidth(2);
            gc.setStroke(Color.GREEN);
            gc.strokeLine(TODALen,(height - 20),TODALen + ALSAcross ,(height - 20) - ALSUp );

        }
    }

    public void landingView(){
        //landing over an object
        if (obPos < (width/2)){
            gc.setLineWidth(3);
            gc.setStroke(Color.DARKGREEN);
            gc.strokeLine(width -1 ,(height - 50), (width - LDALen + 1) ,(height - 50));
            gc.fillText(("LDA:" + runway.getRunway().getLDA() + "m"), (width - (LDALen / 2)) ,(height - 65));

            //if the RESA is less than the distance of runway taken away by the obstacle then that distance is used instead of RESA
            if ((runway.getRunway().getLength() - runway.getRunway().getLDA() - (obPos + (obPos/2) )) > 300){
                gc.setStroke(Color.ORANGE);
                gc.strokeLine( obPos + (obLen/2),
                        (height - 50),
                        ((double) (runway.getRunway().getLength()- runway.getRunway().getLDA())/ runway.getRunway().getLength())*width,
                        (height - 50));

            }
            //landing towards an object
            else{
                gc.setStroke(Color.YELLOW);
                gc.strokeLine((width - LDALen - 1),(height - 50),(width -(LDALen + sixtyLen - 1)),(height - 50));

                gc.setStroke(Color.ORANGE);
                gc.strokeLine((width -(LDALen + sixtyLen + 1)),(height - 50),(width -(LDALen + sixtyLen + RESALen - 1)),(height - 50));
                gc.fillText(("RESA: 240m"), (width -(LDALen + sixtyLen + (RESALen/2))) ,(height - 65));
            }

            gc.setLineWidth(2);
            gc.setStroke(Color.GREEN);
            gc.strokeLine((width - LDALen),(height - 20),(width - LDALen) - ALSAcross,(height - 20) - ALSUp );

        }
        //landing towards an object
        else{
            gc.strokeLine(1,(height - 50), (LDALen-1) ,(height - 50));
            gc.fillText(("LDA:" + runway.getRunway().getLDA() + "m"), (LDALen / 2) ,(height - 65));

            gc.setStroke(Color.YELLOW);
            gc.strokeLine((LDALen+1),(height - 50),(LDALen + sixtyLen - 1),(height - 50));

            gc.setStroke(Color.ORANGE);
            gc.strokeLine((LDALen + sixtyLen + 1),(height - 50),(LDALen + sixtyLen +RESALen - 1),(height - 50));
            gc.fillText(("RESA: 240m"), (LDALen + sixtyLen +(RESALen/2)) ,(height - 65));
            }
        }

        @Override
        public void runwayUpdated(RedeclaredRunway runway, State state){
            representView(runway);
        }
    }

