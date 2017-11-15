package org.usfirst.frc.team610.robot.subsystems;

import constants.ElectricalConstants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    private static DriveTrain instance;

    private Victor leftFront, leftBack, rightFront, rightBack;
    private Encoder leftEnc, rightEnc;

    public DriveTrain() {
	leftFront = new Victor(ElectricalConstants.DRIVE_LEFT_FRONT);
	leftBack = new Victor(ElectricalConstants.DRIVE_LEFT_BACK);
	rightFront = new Victor(ElectricalConstants.DRIVE_RIGHT_FRONT);
	rightBack = new Victor(ElectricalConstants.DRIVE_RIGHT_BACK);
	
	leftEnc = new Encoder(ElectricalConstants.DRIVE_ENC_LEFT_A, ElectricalConstants.DRIVE_ENC_LEFT_B);
	rightEnc = new Encoder(ElectricalConstants.DRIVE_ENC_RIGHT_A, ElectricalConstants.DRIVE_ENC_RIGHT_B);
	leftEnc.setDistancePerPulse(4 * Math.PI / 128.0);
	rightEnc.setDistancePerPulse(4 * Math.PI / 128.0);
    }

    public static DriveTrain getInstance() {
	if (instance == null) {
	    instance = new DriveTrain();
	}
	return instance;
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void resetSensors() {
	leftEnc.reset();
	rightEnc.reset();
    }

    public double getLeftInches() {
	return leftEnc.getDistance();
    }

    public double getRightInches() {
	return -rightEnc.getDistance();
    }

    public double getRightRPM() {
	return rightEnc.getRate();
    }

    public double getLeftRPM() {
	return rightEnc.getRate();
    }

    public void resetEnc() {
	rightEnc.reset();
	leftEnc.reset();
    }

    public void setRight(double power) {
	power *= -1;
	rightFront.set(power);
	rightBack.set(power);
    }

    public void setLeft(double power) {
	power *= -1;
	leftFront.set(power);
	leftBack.set(power);
    }

}
