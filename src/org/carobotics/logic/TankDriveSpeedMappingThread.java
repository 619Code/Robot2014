package org.carobotics.logic;

import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.TankDriveBase;
import org.carobotics.hardware.Joystick;

/**
 * @author CaRobotics
 */
public class TankDriveSpeedMappingThread extends TankDriveMappingThread {

    private final static boolean DEBUG = false;
    protected double maxRPM;

    public TankDriveSpeedMappingThread(TankDriveBase tankDriveBase,
            DriverStation driverStation, double maxRPM, int period, ThreadManager threadManager) {
        super(tankDriveBase, driverStation, period, threadManager);
        this.maxRPM = maxRPM;
    }

    protected void cycle() {
        double scalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Z);
        if(scalePercent < 0.3) scalePercent = 0.3;
        
        try {
            double leftSpeed = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * maxRPM * scalePercent;
            double rightSpeed = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * maxRPM * scalePercent;
            //tankDriveBase.getLeftJaguar().setSpeed(deadband(leftSpeed, -5, 5));
            //tankDriveBase.getRightJaguar().setSpeed(deadband(rightSpeed, -5, 5));
            tankDriveBase.getLeftJaguar().setSpeed(leftSpeed);
            tankDriveBase.getRightJaguar().setSpeed(rightSpeed);
            if (DEBUG) {
                System.out.println("Sending to left Jag: " + leftSpeed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public double getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(double maxRPM) {
        this.maxRPM = maxRPM;
    }

    private double deadband(double value, double min, double max) {
        return (value > min && value < max) ? 0 : value;
    }
}


// Hey Thomas/ whoever is at the lab, how much have you all accomplished tonight?