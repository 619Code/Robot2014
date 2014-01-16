package org.carobotics.logic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.TankDriveBase;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class TankDriveMappingThread extends RobotThread {
    protected TankDriveBase tankDriveBase;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = false;
    private long lastTime = 0;

    public TankDriveMappingThread(TankDriveBase tankDriveBase,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.tankDriveBase = tankDriveBase;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        double scalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Z);
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }

        double leftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;

        try {
            tankDriveBase.getLeftJaguar().setPercent(leftPercent);
        } catch(Exception e) {
            if(firstError || DEBUG) {
                e.printStackTrace();
            }
        }
        try {
            tankDriveBase.getRightJaguar().setPercent(rightPercent);
        } catch(Exception e) {
            if(firstError || DEBUG) {
                e.printStackTrace();
            }
        }

        SmartDashboard.putNumber("Drive - Left", leftPercent);
        SmartDashboard.putNumber("Drive - Right", rightPercent);
        
        if(DEBUG) {
            System.out.println("[TankDriveMappingThread] Left Percent: " + leftPercent + " | Right Percent: " + rightPercent);
        }
    }
}
