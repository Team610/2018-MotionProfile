package motion;

import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import constants.MotionConstants;

public class PathFollower {

	PathSegment lastSeg = null;

	DriveTrain drive;
	Path path;
	double startingAngle;

	public PathFollower(Path path) {
		this.path = path;
		drive = DriveTrain.getInstance();
	}

	public double[] followPath(double scale) {
		PathSegment curSeg = getCurrentSeg(path);

		double leftSpeed = 0;
		double rightSpeed = 0;

		//Path is complete
		if (curSeg == null) {
			return new double[]{leftSpeed, rightSpeed};
		}

		if (curSeg.center == null) {
			//FOLLOW LINE
			double dist = curSeg.start.distance(curSeg.end);
			double[] motionData = MotionProfileUtils.findLineSegmentVels(lastSeg == null ? 0 : lastSeg.endSpeed, curSeg.endSpeed, dist);
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
				leftSpeed = rightSpeed = MotionConstants.Kd * motionData[5] + MotionConstants.Kv * (lastSeg == null ? 0 : lastSeg.endSpeed);
			} else {
				doneSeg(curSeg);
			}

		} else {
			//FOLLOW ARC
			double radius = curSeg.end.distance(curSeg.center);
			double dist = MotionProfileUtils.findArcLength(curSeg.start, curSeg.end, curSeg.center);
			double[] motionData = MotionProfileUtils.findCircleVel(curSeg.endSpeed, radius);
			boolean doneMoveCircle = MotionProfileUtils.doneMoveCircle(dist, drive.getLeftInches(), drive.getRightInches());
			if (!doneMoveCircle) {
//				Point c = curSeg.end;
//				double R = 100;
//				double new_x = curSeg.start.x + R * Math.cos(startingAngle);
//				double new_y = curSeg.start.y + R * Math.sin(startingAngle);
//
//				Point b = lastSeg.start;
//				Point a = lastSeg.end;

				Point a = lastSeg.start;
				Point b = lastSeg.end;
				Point p = curSeg.end;

				double Xa = a.x;
				double Xb = b.x;
				double Xp = p.x;
				double Ya = a.y;
				double Yb = b.y;
				double Yp = p.y;

				double Xab = Xa - Xb;
				double Yap = Ya - Yp;
				double Xap = Xa - Xp;
				double Yab = Ya - Yb;

				double sign = Xab*Yap-Xap*Yab;

//				((p.x - a.x)*(b.y - a.y)-(p.y - a.y)*(b.x-a.x)
				if(sign > 0) {
					leftSpeed = MotionConstants.Kv * motionData[1];
					rightSpeed = MotionConstants.Kv * motionData[0];
				} else {
					leftSpeed = MotionConstants.Kv * motionData[0];
					rightSpeed = MotionConstants.Kv * motionData[1];
				}
			} else {
				doneSeg(curSeg);
			}
		}
		leftSpeed *= scale;
		rightSpeed *= scale;

		return new double[]{leftSpeed, rightSpeed};
	}

	private void doneSeg (PathSegment currentSeg){
		currentSeg.isDone = true;
		lastSeg = currentSeg;
		drive.resetEnc();
	}

	private PathSegment getCurrentSeg(Path path) {
		for(int i = 0; i < path.getSegments().size(); i++){
			if(!path.getSegments().get(i).isDone){
				return path.getSegments().get(i);
			}
		}
		return null;
	}
}