package constants;

public class MotionConstants {

    public final static double MAX_VELOCITY = 1300.0/256.0*60.0*(1/*Wheel Circumference*/); //feet/s
    public final static double MAX_ACCELERATION = 1300.0/4.0*(1/*Wheel Circumference*/); //feet/s/s
    public final static double MAX_DECCELERATION = 1300.0/4.0*(1/*Wheel Circumference*/); //feet/s/s
    public final static double Kv = 1/MAX_VELOCITY;
    public final static double Ka = 1/MAX_ACCELERATION;
    public final static double Kd = 1/MAX_DECCELERATION;
    public final static double ROBOT_WIDTH = 25/12.0; //feet
    
}

