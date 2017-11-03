package motion;

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

	    List<Integer> ind = new ArrayList<>();
	    List<PathSegment> segs = new ArrayList<>();

	    int j = p.getSegments().size() - 1;
	    do {
		ind.add(j);
		segs.add(new PathSegment(getSeg(p.getSegments(), j - 1).end, getSeg(p.getSegments(), j).start,
			getSeg(p.getSegments(), j).endSpeed));
		j--;
	    } while (j >= 1);
	    for (int z = 0; z < ind.size(); z++) {
		p.addSegment(ind.get(z), segs.get(z));
	    }
	}
	Line start = new Line(w.get(0), w.get(1));
	p.addSegment(0, new PathSegment(start.start, start.end, start.speed));

	Line line = new Line(w.get(w.size() - 2), w.get(w.size() - 1));
	p.addSegment(new PathSegment(line.start, line.end, 0));

	p.segments = removeDupes(p.getSegments());
	return p;
    }

    private static Waypoint getPoint(List<Waypoint> w, int i) {
	if (i > w.size())
	    return w.get(w.size() - 1);
	return w.get(i);
    }

    private static PathSegment getSeg(List<PathSegment> w, int i) {
	if (i > w.size())
	    return w.get(w.size() - 1);
	if (i < 0)
	    return w.get(0);
	return w.get(i);
    }

    private static List<PathSegment> removeDupes(List<PathSegment> w) {
	List<Integer> ind = new ArrayList<>();
	for (int i = 0; i < w.size(); i++) {
	    if (w.get(i).posEqual())
		ind.add(i);
	}
	for (int i = ind.size() - 1; i >= 0; i--) {
	    w.remove((int) ind.get(i));
	}
	return w;
    }

}
