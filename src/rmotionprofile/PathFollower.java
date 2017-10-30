package rmotionprofile;

public class PathFollower {

    PathSegment lastSeg = null;
    final double mSpeed = 60;
    final double acc = 60;
    final double dec = 60;

    public boolean followPath(Path path) {
	PathSegment curSeg = getCurrentSeg(path);

	//Path is complete
	if (curSeg == null)
	    return true;

	if (curSeg.center == null) {
	    //FOLLOW LINE
	    double dist = Math.hypot(curSeg.start.x - curSeg.end.x, curSeg.start.y - curSeg.end.y);
	    double[] motionData = MotionProfileUtils.findLineSegmentVels(lastSeg.endSpeed, mSpeed, curSeg.endSpeed, acc,
		    dec, dist);
	    boolean doneLineAcceleration = MotionProfileUtils.doneLineAcc(motionData[0], 0, 0);
	    boolean doneLineCoast = MotionProfileUtils.doneLineAcc(motionData[1], 0, 0);
	    boolean doneLineDecceleration = MotionProfileUtils.doneLineAcc(motionData[2], 0, 0);

	    if (!doneLineAcceleration) {
		//Ka * curSeg.acc + Kv * curSeg.vel
	    } else if (!doneLineCoast) {
		//setSpeed(0); //Coasting NOT BREAK
	    } else if (!doneLineDecceleration) {
		//Ka * curSeg.acc + Kv * lastSeg.vel
	    } else {
		curSeg.isDone = true;
	    }
	} else {
	    //FOLLOW ARC

	}
	if (!(lastSeg.equals(curSeg)))
	    lastSeg = curSeg;
	return false;
    }

    private PathSegment getCurrentSeg(Path path) {
	for (PathSegment seg : path.getSegments())
	    if (!seg.isDone) {
		return seg;
	    }
	return null;
    }
}