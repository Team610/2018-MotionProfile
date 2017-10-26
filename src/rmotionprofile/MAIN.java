package rmotionprofile;

import java.util.ArrayList;

public class MAIN {

    public static void main(String[] args) {
	ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(20,160,0,0));
        sWaypoints.add(new Waypoint(105,90,15,60));
        sWaypoints.add(new Waypoint(125,117,10,60));
        sWaypoints.add(new Waypoint(145,50,30,60));
        sWaypoints.add(new Waypoint(180,90,0,60));
	
	System.out.println(new MotionPather().generatePath(sWaypoints));
//        new MotionPather().generatePath(sWaypoints);
    }

}
