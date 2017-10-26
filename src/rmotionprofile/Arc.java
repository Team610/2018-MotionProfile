package rmotionprofile;

import java.awt.geom.Point2D;

public class Arc {

    Line a;
    Line b;
    Point2D.Double center;
    double radius;
    double speed;

    public Arc(Waypoint a, Waypoint b, Waypoint c) {
	this(new Line(a, b), new Line(b, c));
    }

    public Arc(Line a, Line b) {
	this.a = a;
	this.b = b;
	this.speed = (a.speed + b.speed) / 2;
	this.center = intersect(a.end, translate(a.end, perp(a.slope)), b.start, translate(b.start, perp(b.slope)));
	this.radius = center.distance(a.end);

    }

    public Point2D.Double intersect(Point2D.Double a, Point2D.Double b, Point2D.Double c, Point2D.Double d) {
	final double kEpsilon = 1E-9;
	double i = ((a.x - b.x) * (c.y - d.y) - (a.y - b.y) * (c.x - d.x));
	i = (Math.abs(i) < kEpsilon) ? kEpsilon : i;
	double x = (cross(a, b) * (c.x - d.x) - cross(c, d) * (a.x - b.x)) / i;
	double y = (cross(a, b) * (c.y - d.y) - cross(c, d) * (a.y - b.y)) / i;
	return new Point2D.Double(x, y);
    }

    public Point2D.Double perp(Point2D.Double a) {
	return new Point2D.Double(-a.y, a.x);
    }

    public double cross(Point2D.Double a, Point2D.Double b) {
	return a.x * b.y - a.y * b.x;
    }

    public boolean equals(double a, double b) {
	return Math.abs(a - b) <= 1E-9;
    }

    public double slope(Point2D.Double a, Point2D.Double b) {
	final double kEpsilon = 1E-9;
	if (b.x - a.x > kEpsilon)
	    return (b.y - a.y) / (b.x - a.x);
	else
	    return (b.y - a.y) / kEpsilon;
    }

    public Point2D.Double translate(Point2D.Double a, Point2D.Double b) {
	return new Point2D.Double(a.x + b.x, a.y + b.y);
    }

    public String toString() {
	return "Radius: " + this.radius + " | Center: " + this.center;
    }
}