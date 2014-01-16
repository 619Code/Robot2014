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

    public TalonTankDriveMappingThread(Talon leftTalon, Talon rightTalon, Talon leftTalon2, Talon rightTalon2,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
       // this.driveBase = driveBase;
        this.leftTalon = leftTalon;
        this.rightTalon = rightTalon;
        this.driverStation = driverStation;
        this.leftTalon2 = leftTalon2;
        this.rightTalon2 = rightTalon2;
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

        leftTalon.set(leftPercent);
        rightTalon.set(rightPercent);
        
        if(leftTalon2 != null && rightTalon2 != null){
            leftTalon2.set(leftPercent);
            rightTalon2.set(rightPercent);
            if(DEBUG) System.out.println("[TalonTankDriveMappingThread] Using 2nd motors");
        }
       
        if(DEBUG) {
            System.out.println("[TalonTankDriveMappingThread] Left Percent: " + leftPercent + " | Right Percent: " + rightPercent);
        }
    }
}
