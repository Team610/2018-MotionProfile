package motion;

public class Line {
    Waypoint a;
    Waypoint b;
    Point start;
    Point end;
    Point slope;
    double speed;

    public Line(Waypoint a, Waypoint b) {
	this.a = a;
	this.b = b;
	slope = diff(a.position, b.position);
	start = translate(a.position, scale(slope, a.radius / norm(this.slope)));
	end = translate(b.position, invert(scale(slope, b.radius / norm(this.slope))));
	speed = (a.speed + b.speed) / 2;
    }

    public double norm(Point a) {
	return Math.hypot(a.x, a.y);
    }

    public Point invert(Point a) {
	return new Point(-a.x, -a.y);
    }

    public Point scale(Point a, double s) {
	return new Point(a.x * s, a.y * s);
    }

    public Point translate(Point a, Point b) {
	return new Point(a.x + b.x, a.y + b.y);
    }

    public Point diff(Point a, Point b) {
	return new Point(b.x - a.x, b.y - a.y);
    }

}