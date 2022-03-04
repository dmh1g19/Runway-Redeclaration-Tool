package uk.ac.soton.comp2211.components;

public class SideOnRunway extends Canvas{

    /**
     * Draw the view for side-on runway here -
     */
    private final Runway runway;
    private final ObstacleOnRunway obstacle;
    private final double width;
    private final double height;

    public SideOnRunway(Runway runway, ObstacleOnRunway obstacle, double width, double height){
        this.runway = runway;
        this.obstacle = obstacle;
        this.width = width;
        this.height = height;

        setWidth(width);
        setHeight(height);

        representView();

        //add listener so that everytime runway or obstacle changed by user side on view updates.
    }

    private void representView(){
        var gc = getGrapicsContext2D();
        gc.clearRect(0,0,width,height);

    }



}
