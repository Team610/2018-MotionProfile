package rmotionprofile;

import java.awt.geom.Point2D;

public class Line {
    Waypoint a;
    Waypoint b;
    Point2D.Double start;
    Point2D.Double end;
    Point2D.Double slope;
    double speed;

    public Line(Waypoint a, Waypoint b) {
	this.a = a;
	this.b = b;
	slope = diff(a.position,b.position);
	start = translate(a.position, scale(slope, a.radius/norm(this.slope)));
	end = translate(b.position, invert(scale(slope, b.radius/norm(this.slope))));

    }
    
    public double norm(Point2D.Double a) {
	return Math.hypot(a.x, a.y);
    }
    
    public Point2D.Double invert(Point2D.Double a){
	return new Point2D.Double(-a.x, -a.y);
    }
    
    public Point2D.Double scale(Point2D.Double a, double s){
	return new Point2D.Double(a.x * s, a.y * s);
    }
    
    public Point2D.Double translate(Point2D.Double a, Point2D.Double b){
	return new Point2D.Double(a.x + b.x, a.y+b.y);
    }
    public Point2D.Double diff(Point2D.Double a, Point2D.Double b) {
	return new Point2D.Double(b.x - a.x, b.y - a.y);
    }

}