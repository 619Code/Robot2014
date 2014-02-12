package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.TalonDriveBase;
import org.carobotics.hardware.Talon;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class TalonTankDriveMappingThread extends RobotThread {
    protected TalonDriveBase driveBase;
    protected Talon leftTalon;
    protected Talon rightTalon;
    protected Talon leftTalon2, rightTalon2;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;

    public TalonTankDriveMappingThread(TalonDriveBase driveBase,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.driveBase = driveBase;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        double rightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Z);
        double leftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
        
        if(rightScalePercent < 0.3) {
            rightScalePercent = 0.3;
        }
        if(leftScalePercent < 0.3){
            rightScalePercent = 0.3;
        }

        double leftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * leftScalePercent;
        double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * rightScalePercent;

        driveBase.getLeftTalon().set(leftPercent);
        driveBase.getRightTalon().set(rightPercent);
        
        if(driveBase.getLeftTalon2() != null && driveBase.getRightTalon2() != null){
            driveBase.getLeftTalon2().set(leftPercent);
            driveBase.getRightTalon2().set(rightPercent);
            if(DEBUG) System.out.println("[TalonTankDriveMappingThread] Using 2nd motors");
        }
       
        if(DEBUG) {
            System.out.println("[TalonTankDriveMappingThread] Left Percent: " + leftPercent + " | Right Percent: " + rightPercent);
        }
    }
}
