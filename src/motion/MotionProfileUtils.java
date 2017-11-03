package motion;

import constants.MotionConstants;

public class MotionProfileUtils {
    /**
     * 
     * @param vInitial - Initial Velocity in in/s
     * @param vFinal - Final Velocity in in/s
     * @param dist - Total distance
     * @return double[] of 6 values, distance To Accelerate, Acceleration Amount, distance To Coast, coasting Velocity, distance To Decelerate, amount of Decceleration
     */
    public static double[] findLineSegmentVels(double vInitial, double vFinal, double dist) {
	double vCoast = getCoastVelocity(vInitial, vFinal, dist);
	double distAcc = (vCoast * vCoast - vInitial * vInitial) / (2 * MotionConstants.MAX_ACCELERATION);
	double distDec = (vCoast * vCoast - vFinal * vFinal) / (2 * MotionConstants.MAX_DECCELERATION);
	double distCoast = dist - distAcc - distDec;
	return new double[] { distAcc, MotionConstants.MAX_ACCELERATION, distCoast, vCoast, distDec,
		MotionConstants.MAX_DECCELERATION };
    }

    /**
     * 
     * @param vInitial - Initial Velocity in in/s
     * @param vFinal - Final Velocity in in/s
     * @param dist - Total distance
     * @return the coasting velocity in in/s
     */
    private static double getCoastVelocity(double vInitial, double vFinal, double dist) {
	double dTwo = (((Math.pow(vInitial, 2) - Math.pow(vFinal, 2)) / (2 * MotionConstants.MAX_ACCELERATION))
		+ dist) / 2.0;
	double dOne = dist - dTwo;
	double vCoast = Math.sqrt(Math.pow(vInitial, 2) + 2 * MotionConstants.MAX_ACCELERATION * dOne);
	//vCoast is the point of intersection in a triangle motion profile. By Removing 90% it creates a trapezoidal motion profile. If vCoast * 90% is still greater than MAX_VELOCITY return the MAX_VELOCITY
	return Math.min(vCoast * 0.9, MotionConstants.MAX_VELOCITY);
    }

    public static boolean doneLineAcc(double distAcc, double encL, double encR) {
	return distAcc <= (encL + encR) / 2;
    }

    public static boolean doneLineCoast(double distCoast, double encL, double encR) {
	return distCoast <= (encL + encR) / 2;
    }

    public static boolean doneLineDec(double distDec, double encL, double encR) {
	return distDec <= (encL + encR) / 2;
    }

    public static boolean doneMoveCircle(double arcLength, double encL, double encR) {
	return arcLength <= (encL + encR) / 2;
    }

    /**
     * 
     * @param vRobot - Robot velocity in in/s
     * @param rad - radius of the circle in in
     * @return double[]  of 2 values, inner side velocity and outer side velocity both in in/s
     */
    public static double[] findCircleVel(double vRobot, double rad) {
	double vOut = vRobot * (rad + MotionConstants.ROBOT_WIDTH / 2) / rad;
	double vIn = vRobot * (rad - MotionConstants.ROBOT_WIDTH / 2) / rad;
	return new double[] { vIn, vOut };
    }

    /**
     * 
     * @param start - starting Point of the arc
     * @param end - ending Point of the arc
     * @param center - center Point of the arc
     * @return the perimeter of the arc
     */
    public static double findArcLength(Point start, Point end, Point center) {
	double rad = start.distance(center);
	double circ = 2 * Math.PI * rad;
	double ang1 = Math.atan2(center.y - start.y, center.x - start.x);
	double ang2 = Math.atan2(center.y - end.y, center.x - end.y);

	return circ * Math.abs(ang1 - ang2) / (2 * Math.PI);
    }
}
