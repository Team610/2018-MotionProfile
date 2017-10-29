package rmotionprofile;

public class PathFollower {

    PathSegment lastSeg = null;
    final double MAX_SPEED = 60;
    final double MAX_ACCELERATION = 60;
    final double MAX_DECCELERATION = 60;
    final double ROBOT_WIDTH = 60;
    final double Kv = 60;
    final double Ka = 60;

    public boolean followPath(Path path) {
	PathSegment curSeg = getCurrentSeg(path);

	//Path is complete
	if (curSeg == null)
	    return true;

	if (curSeg.center == null) {
	    //FOLLOW LINE
	    double dist = Math.hypot(curSeg.start.x - curSeg.end.x, curSeg.start.y - curSeg.end.y);
	    double[] motionData = MotionProfileUtils.findLineSegmentVels(lastSeg.endSpeed, MAX_SPEED, curSeg.endSpeed, MAX_ACCELERATION, MAX_DECCELERATION, dist);
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
	    double radius = curSeg.start.distance(curSeg.center);
	    double dist = MotionProfileUtils.findArcLength(curSeg.start, curSeg.end, curSeg.center, radius);
	    double[] motionData = MotionProfileUtils.findCircleVel(curSeg.endSpeed, ROBOT_WIDTH, radius);
	    boolean doneMoveCircle = MotionProfileUtils.doneMoveCircle(dist, GetLeftEnc(), getRightEnc());

	    if (!doneMoveCircle) {
		double lastX = lastSeg.end.getX();
		double lastY = lastSeg.end.getY();
		double curX = curSeg.end.getX();
		double curY = curSeg.end.getY();

		double leftSpeed = 0;
		double rightSpeed = 0;
		
		if (curX - lastX > 0) {
		    if (curY - lastY > 0) {
			//Forward Right
			leftSpeed = Kv * motionData[1] + Ka * MAX_ACCELERATION;
			rightSpeed = Kv * motionData[0] + Ka * MAX_ACCELERATION;
		    } else {
			//Backward Right
			leftSpeed = -(Kv * motionData[1] + Ka * MAX_ACCELERATION);
			rightSpeed = -(Kv * motionData[0] + Ka * MAX_ACCELERATION);
		    }
		} else {
		    if (curY - lastY > 0) {
			//Forward Left
			leftSpeed = Kv * motionData[0] + Ka * MAX_ACCELERATION;
			rightSpeed = Kv * motionData[1] + Ka * MAX_ACCELERATION;
		    } else {
			//Backward Left
			leftSpeed = -(Kv * motionData[0] + Ka * MAX_ACCELERATION);
			rightSpeed = -(Kv * motionData[1] + Ka * MAX_ACCELERATION);
		    }
		}
		
		setLeft(leftSpeed);
		setRight(rightSpeed);
		
	    }

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