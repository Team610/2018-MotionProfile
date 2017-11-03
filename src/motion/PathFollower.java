package motion;

import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import constants.MotionConstants;

public class PathFollower {

    PathSegment lastSeg = null;
    DriveTrain drive;

    Path path;

    public PathFollower(Path path) {
	this.path = path;
	drive = DriveTrain.getInstance();
    }

    public boolean followPath() {
	PathSegment curSeg = getCurrentSeg(path);

	double leftSpeed = 0;
	double rightSpeed = 0;

	//Path is complete
	if (curSeg == null)
	    return true;

	if (curSeg.center == null) {
	    //FOLLOW LINE
	    double dist = curSeg.start.distance(curSeg.start);
	    double[] motionData = MotionProfileUtils.findLineSegmentVels(lastSeg == null ? 0 : lastSeg.endSpeed,
		    curSeg.endSpeed, dist);
	    boolean doneLineAcceleration = MotionProfileUtils.doneLineAcc(motionData[0], drive.getLeftInches(),
		    drive.getRightInches());
	    boolean doneLineCoast = MotionProfileUtils.doneLineAcc(motionData[0] + motionData[2], drive.getLeftInches(),
		    drive.getRightInches());
	    boolean doneLineDecceleration = MotionProfileUtils.doneLineAcc(
		    motionData[0] + motionData[2] + motionData[4], drive.getLeftInches(), drive.getRightInches());
	    if (!doneLineAcceleration) {
		leftSpeed = rightSpeed = MotionConstants.Ka * motionData[1] + MotionConstants.Kv * motionData[3];
	    } else if (!doneLineCoast) {
		leftSpeed = rightSpeed = MotionConstants.Kv * motionData[3];
	    } else if (!doneLineDecceleration) {
		leftSpeed = rightSpeed = MotionConstants.Kd * -motionData[5] + MotionConstants.Kv * lastSeg.endSpeed;
	    } else {
		curSeg.isDone = true;
	    }
	} else {
	    //FOLLOW ARC
	    double radius = curSeg.start.distance(curSeg.center);
	    double dist = MotionProfileUtils.findArcLength(curSeg.start, curSeg.end, curSeg.center);
	    double[] motionData = MotionProfileUtils.findCircleVel(curSeg.endSpeed, radius);
	    boolean doneMoveCircle = MotionProfileUtils.doneMoveCircle(dist, drive.getLeftInches(),
		    drive.getRightInches());
	    if (!doneMoveCircle) {
		double lastX = lastSeg.end.getX();
		double curX = curSeg.end.getX();

		if (curX - lastX > 0) {
		    leftSpeed = MotionConstants.Kv * motionData[1];
		    rightSpeed = MotionConstants.Kv * motionData[0];
		} else {
		    //Forward Left
		    leftSpeed = MotionConstants.Kv * motionData[0];
		    rightSpeed = MotionConstants.Kv * motionData[1];
		}
	    } else {
		curSeg.isDone = true;
	    }
	}

	System.out.println("Left Speed: " + leftSpeed + " | Right Speed: " + rightSpeed);

	if (lastSeg == null || !(lastSeg.equals(curSeg))) {
	    lastSeg = curSeg;
	    System.out.println("set");
	}
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