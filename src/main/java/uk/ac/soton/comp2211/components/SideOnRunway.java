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
    private double TORALen;
    private double ASDALen;
    private double LDALen;
    private double sixtyLen;
    private double RESALen;
    private double scaledDTL;
    private double scaledBlast;
    private double scaledRESA;
    private double clearwayLength;
    private double ALSUp;
    private double ALSAcross;
    private double overallLength;
    private Direction dToObstacle;//direction relative to obstacle
    private Character rwayDirection; //direction of the runway
    private boolean reflected =false;

    private State state;

    private GraphicsContext gc =  getGraphicsContext2D();

    public SideOnRunway(double width, double height){
        super(width,height);

        this.width = getWidth();
        this.height = getHeight();


    }


    @Override
    public void redraw()
    {
        draw(runway,state);
    }

    //need to add stopway and clearway to the view.
    @Override
    public void draw(RedeclaredRunway runway1, RedeclaredRunway runway2,State state) {

        //Moved from constructor
        this.runway = runway1;
        this.state = state;
        this.obstacle = runway.getObstacle();
        this.dToObstacle = runway.getDirection();
        this.rwayDirection = runway.getRunway().getName().charAt(2);

        if(runway.getRunway().getBearing() >181){
            reflected=true;
            this.setScaleX(-1);
        }





        width=getWidth();
        height= getHeight();
        overallLength = runway.getRunway().getLength() + runway.getRunway().getClearwayLength();


        obPos = (((double) obstacle.getPosition())/overallLength)*width; //relative position of object
        obLen = (((double)obstacle.getLength())/overallLength)*width; //relative length of object
        obHeight = (((double)obstacle.getHeight())/overallLength)*height; //relative height of object


        //relative sizes for TODA, 60m and the RESA are calculated.
        TODALen = ((double) runway.getRunway().getTODA() / overallLength) * width;
        TORALen = ((double) runway.getRunway().getTORA() / overallLength) * width;
        ASDALen = ((double) runway.getRunway().getASDA() / overallLength) * width;
        LDALen = ((double) runway.getRunway().getLDA() / overallLength) * width;
        sixtyLen = (60.0/overallLength)*width;
        RESALen = (240.0/overallLength)*width;
        clearwayLength = ((double) runway.getRunway().getClearwayLength() / overallLength) * width;
        scaledDTL = (runway.getRunway().getDTL() /overallLength ) *width;
        scaledBlast =(blastProtection /overallLength)*width;
        scaledRESA =(300 /overallLength)*width;


        // length and height of ALS slope relative to view size are calculated.
        ALSUp = 4 * obstacle.getHeight();
        ALSAcross = 50 * ALSUp;
        ALSUp = (ALSUp / overallLength) * height * 10;
        ALSAcross = (ALSAcross /overallLength) * height;


        //clear canvas before drawing -- for when you have to redraw
        gc.clearRect(0,0, getWidth(), getHeight());

        //Original Function
        gc.save();

        gc.transform(new Affine(new Scale(scale,scale,width/2,height/2)));
        gc.transform(new Affine(new Translate(x,y)));

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0, width, height);


        gc.setFill(Color.BLACK);
        gc.fillRect(0, (height - 20), width - clearwayLength , 20);


        //DTL

        gc.setFill(Color.YELLOW);
        gc.fillRect(0, (height - 20),scaledDTL  , 20);


        gc.setFill(Color.DARKGRAY);
        gc.fillRect(width - clearwayLength,(height - 20),clearwayLength,20 );


        //the obstructions dimensions relative to the view size are calculated.
        gc.setFill(Color.RED);
        gc.fillRect((obPos - (obLen / 2)), (height -((obHeight*10) + 20)), obLen, obHeight*10);
        gc.setFill(Color.BLACK);
        gc.strokeLine(obPos,(height-60), obPos, (height - 80));
        double[] xPoints = {obPos,obPos+5,obPos-5};
        double[] yPoints = {(height - 58),(height - 65), (height - 65) };
        gc.fillPolygon(xPoints ,yPoints , 3);

        if(reflected){
            gc.save();
            gc.transform(new Affine(new Scale(-1,1,obPos,(height - 85) )));
            gc.fillText("Obstacle",obPos - 25, (height - 85));
            gc.restore();
        }else{
            gc.fillText("Obstacle",obPos - 25, (height - 85));
        }

        //right now just shows take off view but buttons will be installed to switch between the two
        if(state==State.TAKEOFF){
            takeOffView();
        }else{
            landingView();
        }

        gc.restore(); //EVERYTHING PAST THIS POINT WILL NOT BE SCALED


        //A compass in the top right of the screen that shows the bearing of the runway
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.fillOval(width - 100, height/10 , 50,50);
        gc.strokeOval(width - 100, height/10 , 50,50);
        gc.setStroke(Color.BLUE);
        gc.save();
        if(reflected){
            gc.transform(new Affine(new Rotate(runway.getRunway().getBearing()+180,(width - 75),(height/10 +25))));
        } else {
            gc.transform(new Affine(new Rotate(runway.getRunway().getBearing(),(width - 75),(height/10 +25))));
        }
        gc.strokeLine(width - 75, height/10 +25, width - 75, height/10 + 7  );
        gc.restore();
        gc.setStroke(Color.RED);
        gc.strokeLine(width- 75, height/10 +25, width- 75, height/10 + 7  );
        gc.setFill(Color.RED);

        if(reflected){
            gc.save();
            gc.transform(new Affine(new Scale(-1,1,width - 75,height/10 + 7 )));
            gc.fillText("N" ,width- 79,height/10 -5 );
            gc.fillText( runway.getRunway().getBearing()+"°",width - 45,height/10 +30 );
            gc.fillText("Bearing" ,width - 45,height/10 + 20 );
            gc.restore();
        }else{
            gc.save();
            gc.fillText("N" ,width- 79,height/10 -5 );
            gc.fillText( runway.getRunway().getBearing()+"°",width - 45,height/10 +30 );
            gc.fillText("Bearing" ,width - 45,height/10 + 20 );
            gc.restore();
        }

        gc.setFill(Color.BLACK);
        if(reflected){
            gc.save();
            gc.transform(new Affine(new Scale(-1,1,70,50)));
            gc.fillText("Width : Height = 1 : 10",0,15);
            gc.restore();
        }else{
            gc.fillText("Width : Height = 1 : 10",0,15);
        }


    }

    //shows planes taking off away from and towards and obstruction.
    public void takeOffView(){

        gc.setFill(Color.BLACK);

        gc.setLineWidth(3);
        gc.setStroke(Color.DARKGREEN);
        //taking off away from an obstruction
        if (dToObstacle == Direction.AWAYOVER){
            double blastAllowance = Double.max(scaledBlast,scaledRESA) ;
            gc.setLineWidth(3);
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(obPos+(obLen/2), (height - 50),obPos+(obLen/2) + blastAllowance ,(height - 50));
            writeText(("Blast Allowance / RESA: "+ blastProtection),obPos+(obLen/2) + (blastAllowance/2),(height - 65) ,reflected );

            //TODA
            gc.setStroke(TODA_COLOR);
            gc.strokeLine(obPos+(obLen/2) + blastAllowance, (height -50),obPos+(obLen/2) + blastAllowance+TODALen, (height - 50));
            writeText(("TODA:" + runway.getRunway().getTODA() + "m"), obPos+(obLen/2) + blastAllowance+(TODALen/2) ,(height - 65),reflected);

            //TORA
            gc.setStroke(TORA_COLOR);
            gc.strokeLine(obPos+(obLen/2) + blastAllowance, (height -80),obPos+(obLen/2) + blastAllowance+TORALen, (height - 80));
            writeText(("TORA:" + runway.getRunway().getTORA() + "m"), obPos+(obLen/2) + blastAllowance+(TORALen/2) ,(height - 95),reflected);

            //ASDA
            gc.setStroke(ASDA_COLOR);
            gc.strokeLine(obPos+(obLen/2) + blastAllowance, (height -110),obPos+(obLen/2) + blastAllowance+ASDALen, (height - 110));
            writeText(("ASDA:" + runway.getRunway().getASDA() + "m"), obPos+(obLen/2) + blastAllowance+(ASDALen/2) ,(height - 125),reflected);

        }
        //taking off towards an obstruction
        else{
            //TODA
            gc.setStroke(TODA_COLOR);
            gc.strokeLine(1,(height - 50), (TODALen-1) ,(height - 50));
            writeText(("TODA:" + runway.getRunway().getTODA() + "m"), (TODALen / 2) ,(height - 65),reflected);

            //TORA
            gc.setStroke(TORA_COLOR);
            gc.strokeLine(1,(height - 80), (TORALen-1) ,(height - 80));
            writeText(("TORA:" + runway.getRunway().getTORA() + "m"), (TORALen / 2) ,(height - 95),reflected);

            //ASDA
            gc.setStroke(ASDA_COLOR);
            gc.strokeLine(1,(height - 110), (ASDALen-1) ,(height - 110));
            writeText(("ASDA:" + runway.getRunway().getASDA() + "m"), (ASDALen / 2) ,(height - 125),reflected);



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

                writeText(("RESA: 240m"), (TODALen + sixtyLen +(RESALen/2)) ,(height - 65),reflected);



            }

            gc.setLineWidth(2);
            gc.setStroke(Color.GREEN);
            gc.strokeLine(TODALen,(height - 20),TODALen + ALSAcross ,(height - 20) - ALSUp );

        }


    }

    public void landingView(){
        //landing over an object
        if (dToObstacle == Direction.AWAYOVER){
            gc.setLineWidth(3);
            gc.setStroke(Color.DARKGREEN);
            gc.strokeLine(width -1 ,(height - 50), (width - LDALen + 1) ,(height - 50));
            writeText(("LDA:" + runway.getRunway().getLDA() + "m"), (width - (LDALen / 2)) ,(height - 65),reflected);

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
                writeText(("RESA: 240m"), (width -(LDALen + sixtyLen + (RESALen/2))) ,(height - 65),reflected);
            }

            gc.setLineWidth(2);
            gc.setStroke(Color.GREEN);
            gc.strokeLine((width - LDALen),(height - 20),(width - LDALen) - ALSAcross,(height - 20) - ALSUp );

        }
        //landing towards an object
        else{
            gc.strokeLine(1,(height - 50), (LDALen-1) ,(height - 50));
            writeText(("LDA:" + runway.getRunway().getLDA() + "m"), (LDALen / 2) ,(height - 65),reflected);

            gc.setStroke(Color.YELLOW);
            gc.strokeLine((LDALen+1),(height - 50),(LDALen + sixtyLen - 1),(height - 50));

            gc.setStroke(Color.ORANGE);
            gc.strokeLine((LDALen + sixtyLen + 1),(height - 50),(LDALen + sixtyLen +RESALen - 1),(height - 50));
            writeText(("RESA: 240m"), (LDALen + sixtyLen +(RESALen/2)) ,(height - 65),reflected);
            }
        }
        

        private void writeText(String s, double x , double y , boolean reflected){
            if (reflected){
                reflectText(s,x,y);

            }else{
                gc.fillText(s,x,y);
            }
        }

        private void reflectText (String s, double x , double y){
            gc.save();
            gc.transform(new Affine(new Scale(-1,1,x,y)));
            gc.fillText(s,x,y);
            gc.restore();
        }






    }

