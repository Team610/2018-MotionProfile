package rmotionprofile;

import java.awt.geom.Point2D;

public class Waypoint {

    Point2D.Double position;
    double radius;
    double speed;
    String marker;

    public Waypoint(double x, double y, double r, double s) {
        position = new Point2D.Double(x, y);
        radius = r;
        speed = s;
    }
    
    public Waypoint(double x, double y, double r, double s, String m) {
        this(x,y,r,s);
        marker = m;
    }
    
}
