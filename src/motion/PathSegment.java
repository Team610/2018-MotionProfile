package motion;

public class PathSegment {

    public Point start;
    public Point end;
    public Point center;
    public double endSpeed;
    public boolean isDone = false;

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

    public void extend(double n){
        double angle = Math.atan((end.x-start.x)/(end.y-start.y));
        double new_x = end.x + n * Math.cos(angle);
        double new_y = end.y + n * Math.sin(angle);
        end = new Point(new_x, new_y);
    }

}
