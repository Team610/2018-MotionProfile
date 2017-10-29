package rmotionprofile;

import java.awt.geom.Point2D;

public class PathSegment {

    Point2D.Double start;
    Point2D.Double end;
    Point2D.Double center;
    double endSpeed;
    boolean isDone = false;
    
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
    
    public boolean equals(PathSegment p){
	return equals(this.start.getX(),p.start.getX()) &&
		equals(this.start.getY(),p.start.getY()) &&
		equals(this.end.getX(),p.end.getX()) &&
		equals(this.end.getY(),p.end.getY()) &&
		equals(this.endSpeed,p.endSpeed);
    }
   
    private boolean equals(double x, double y){
	return Math.abs(x-y) < 1E-6;
    }
    
}