package rmotionprofile;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class MotionPather {

    public Path generatePath(List<Waypoint> w) {
	Path p = new Path();
	if (w.size() < 2)
	    throw new Error("Path must contain at least 2 waypoints");
	int i = 0;
	if (w.size() > 2) {
	    do {
		Arc arc = new Arc(getPoint(w, i), getPoint(w, i + 1), getPoint(w, i + 2));
		p.addSegment(new PathSegment(arc.a.end, arc.b.start, arc.center, arc.speed));
		i++;
	    } while (i < w.size() - 2);
	}
	
	List<Integer> ind = new ArrayList<>();
	List<PathSegment> segs = new ArrayList<>();
	
	int j = p.getSegments().size()-1;
	do {
	    ind.add(j);
	    segs.add(new PathSegment(p.getSegments().get(j-1).end, p.getSegments().get(j).start, 0));
	    j--;
	} while (j >= 1);
	for (int z = 0; z < ind.size(); z++) {
	    p.addSegment(ind.get(z),segs.get(z));
	}
	
	Line lineStart = new Line(w.get(0), w.get(1));
	p.addSegment(0,new PathSegment(lineStart.start, lineStart.end, 0));
	
	Line line = new Line(w.get(w.size() - 2), w.get(w.size() - 1));
	p.addSegment(new PathSegment(line.start, line.end, 0));
	return p;
    }

    private static Waypoint getPoint(List<Waypoint> w, int i) {
	if (i > w.size())
	    return w.get(w.size() - 1);
	return w.get(i);
    }

}
