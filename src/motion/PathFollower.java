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
	if (curSeg == null){
	    drive.setLeft(0);
	    drive.setRight(0);
	    System.out.println("DONE");
	    return true;
	}
	
	if (curSeg.center == null) {
	    //FOLLOW LINE
	    double dist = curSeg.start.distance(curSeg.end);
	    System.out.println("Dist Remaining: " + (dist - (drive.getLeftInches()+drive.getRightInches())/2) + " inches");
	    double[] motionData = MotionProfileUtils.findLineSegmentVels(lastSeg == null ? 0 : lastSeg.endSpeed,
		    curSeg.endSpeed, dist);
	    boolean doneLineAcceleration = MotionProfileUtils.doneLineAcc(motionData[0], drive.getLeftInches(),
		    drive.getRightInches());
	    boolean doneLineCoast = MotionProfileUtils.doneLineAcc(motionData[0] + motionData[2], drive.getLeftInches(),
		    drive.getRightInches());
	    boolean doneLineDecceleration = MotionProfileUtils.doneLineAcc(
		    motionData[0] + motionData[2] + motionData[4], drive.getLeftInches(), drive.getRightInches());
	    System.out.println(motionData[2]);
	    if (!doneLineAcceleration) {
		leftSpeed = rightSpeed = MotionConstants.Ka * motionData[1] + MotionConstants.Kv * motionData[3];
	    } else if (!doneLineCoast) {
		leftSpeed = rightSpeed = MotionConstants.Kv * motionData[3];
	    } else if (!doneLineDecceleration) {
		leftSpeed = rightSpeed = MotionConstants.Kd * -motionData[5] + MotionConstants.Kv * (lastSeg == null ? 0 : lastSeg.endSpeed);
	    } else {
		doneSeg(curSeg);
	    }
	} else {
	    //FOLLOW ARC
	    double radius = curSeg.start.distance(curSeg.center);
	    double dist = MotionProfileUtils.findArcLength(curSeg.start, curSeg.end, curSeg.center);
	    System.out.println("Dist Remaining: " + (dist - (drive.getLeftInches()+drive.getRightInches())/2) + " inches");
	    double[] motionData = MotionProfileUtils.findCircleVel(curSeg.endSpeed, radius);
	    boolean doneMoveCircle = MotionProfileUtils.doneMoveCircle(dist, drive.getLeftInches(),
		    drive.getRightInches());
	    if (!doneMoveCircle) {
		double lastX = curSeg.start.getX();
		double lastY = curSeg.start.getY();
		double curX = curSeg.end.getX();
		double curY = curSeg.end.getY();
		if (curX - lastX > 0 || curY - lastY > 0) {
		    leftSpeed = MotionConstants.Kv * motionData[1];
		    rightSpeed = MotionConstants.Kv * motionData[0];
		} else {
		    //Forward Left
		    leftSpeed = MotionConstants.Kv * motionData[0];
		    rightSpeed = MotionConstants.Kv * motionData[1];
		}
	    } else {
		doneSeg(curSeg);
	    }
	}

	leftSpeed *= 0.25;
	rightSpeed *= 0.25;

	if (lastSeg == null) {
	    lastSeg = curSeg;
	}
	
	drive.setLeft(leftSpeed);
	drive.setRight(rightSpeed);
	
	return false;
    }

    private void doneSeg (PathSegment currentSeg){
	currentSeg.isDone = true;
	lastSeg = currentSeg;
	drive.resetEnc();
	System.out.println("set");
    }
    
    private PathSegment getCurrentSeg(Path path) {
	for(int i = 0; i < path.getSegments().size(); i++){
	    if(!path.getSegments().get(i).isDone){
		System.out.println("Index at: " + i + " is the current segment");
		return path.getSegments().get(i);
	    }
	}
	return null;
    }
}