package motion;

import java.util.ArrayList;
import java.util.List;

public class Path {

    List<PathSegment> segments;

    public Path() {
	segments = new ArrayList<PathSegment>();
    }

    public void addSegment(PathSegment p) {
	segments.add(p);
    }

    public void addSegment(int i, PathSegment p) {
	segments.add(i, p);
    }

    public List<PathSegment> getSegments() {
	return this.segments;
    }

    public String toString() {
	String f = "";
	for (PathSegment s : segments) {
	    f += s;
	}
	return f;
    }

}
