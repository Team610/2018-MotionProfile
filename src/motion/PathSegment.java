package motion;

public class PathSegment {

    Point start;
    Point end;
    Point center;
    double endSpeed;
    boolean isDone = false;

    /**
     * Constructor for a line segment
     * 
     */
    public PathSegment(Point start, Point end, double endSpeed) {
	this.start = start;
	this.end = end;
	this.endSpeed = endSpeed;
    }

    /**
     * Constructor for a arc segment
     * 
     */
    public PathSegment(Point start, Point end, Point center, double endSpeed) {
	this.start = start;
	this.end = end;
	this.center = center;
	this.endSpeed = endSpeed;
    }

    public String toString() {
	return "Start: " + start + " | " + "end: " + end + " | " + "Center: " + center + " | " + "End Speed: "
		+ endSpeed + "\n";
    }

    public boolean equals(PathSegment p) {
	return equals(this.start.getX(), p.start.getX()) && equals(this.start.getY(), p.start.getY())
		&& equals(this.end.getX(), p.end.getX()) && equals(this.end.getY(), p.end.getY())
		&& equals(this.endSpeed, p.endSpeed);
    }

    public boolean posEqual() {
	return equals(this.start.getX(), this.end.getX()) && equals(this.start.getY(), this.end.getY());
    }

    private boolean equals(double x, double y) {
	return Math.abs(x - y) < 1E-9;
    }

}