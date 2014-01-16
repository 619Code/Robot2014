package org.carobotics.logic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.Victor;
import org.carobotics.subsystems.DriverStation;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class MotorTestMappingThread extends RobotThread {
    protected Victor victor;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;

    public MotorTestMappingThread(Victor victor,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.victor = victor;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }

        double percent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;

        try {
            victor.set(percent);
        } catch(Exception e) {
            if(firstError || DEBUG) {
                e.printStackTrace();
            }
        }

        SmartDashboard.putNumber("Drive - percent", percent);
        
        if(DEBUG) {
            System.out.println("[MotorTestMappingThread] Left Percent: " + percent);
        }
    }
}
