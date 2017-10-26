package rmotionprofile;

import java.awt.geom.Point2D;

public class PathSegment {

    Point2D.Double start;
    Point2D.Double end;
    Point2D.Double center;
    double endSpeed;
    
    /**
     * Constructor for a line segment
     * 
     */
    public PathSegment (Point2D.Double start, Point2D.Double end, double endSpeed) {
   	this.start = start;
   	this.end = end;
   	this.endSpeed = endSpeed;
       }
    
    /**
     * Constructor for a arc segment
     * 
     */
    public PathSegment (Point2D.Double start, Point2D.Double end, Point2D.Double center, double endSpeed) {
	this.start = start;
	this.end = end;
	this.center = center;
	this.endSpeed = endSpeed;
    }
    
    public String toString(){
	return "Start: " + start + " | " + "end: " + end + " | " + "Center: " + center + " | " + "End Speed: " + endSpeed + "\n";
    }
   
    
}