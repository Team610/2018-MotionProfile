package motion;

public class Waypoint {

    public Point position;
    double radius;
    double speed;
    String marker;

    public Waypoint(double x, double y, double r, double s) {
	position = new Point(x, y);
	radius = r;
	speed = s;
    }

    public Waypoint(double x, double y, double r, double s, String m) {
	this(x, y, r, s);
	marker = m;
    }

}
