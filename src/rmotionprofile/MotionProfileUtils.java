package rmotionprofile;

import java.awt.geom.Point2D;

public class MotionProfileUtils {
    static double[] findLineSegmentVels(double vInt, double vCoast, double vFin, double aAcc, double aDec, double dist) {
	double distAcc = (vCoast*vCoast-vInt*vInt)/(2*aAcc);
	double distDec = (vCoast*vCoast - vFin*vFin)/(2*aDec);
	double distCoast = dist-distAcc-distDec;
	return new double[]{distAcc, distDec, distCoast};
    }
    
    static boolean doneLineAcc(double distAcc, double encL, double encR) {
	return equal(distAcc, (encL+encR)/2);
    }
    
    static boolean doneLineCoast (double distCoast, double encL, double encR) {
	return equal(distCoast, (encL+encR)/2);
    }
    
    static boolean doneLineDec(double distDec, double encL, double encR) {
	return equal(distDec, (encL+encR)/2);
    }
    
    static boolean doneMoveCircle (double arcLength, double encL, double encR) {
	return equal(arcLength, (encL+encR)/2);
    }
    
    static double[] findCircleVel(double vRobot, double width, double rad) {
	double vOut = vRobot*(rad+width/2)/rad;
	double vIn = vRobot*(rad-width/2)/rad;
	return new double[]{vIn, vOut};
    }
    
    static double findArcLength(Point2D.Double start, Point2D.Double end, Point2D.Double center, double rad) {
	double circ = 2*Math.PI*rad;
	double ang1 = Math.atan2(center.y-start.y, center.x-start.x);
	double ang2 = Math.atan2(center.y - end.y, center.x-end.y);
	
	return circ*Math.abs(ang1-ang2)/(2*Math.PI);
    }
    
    static boolean equal(double a, double b) {
	return Math.abs(a-b) < 0.01;
    }
}
