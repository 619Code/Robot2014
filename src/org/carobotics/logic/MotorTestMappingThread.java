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
    protected Victor victor, victor2;  //adds motor controllers
    protected DriverStation driverStation;  //adds driverstation
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;

    public MotorTestMappingThread(Victor victor, Victor victor2, 
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.victor = victor;  //makes them objects
        this.victor2 = victor2;  
        this.driverStation = driverStation;
    }

    protected void cycle() {
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);  // makes sure the robot can always move
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }

        double percent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent; // sets up joysticks
        double secondPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;

        try {
            victor.set(percent);
            victor2.set(secondPercent);
        } catch(Exception e) {
            if(firstError || DEBUG) {
                e.printStackTrace();
            }
        }

        SmartDashboard.putNumber("Drive - percent", percent);
        SmartDashboard.putNumber("Drive - secondPercent", secondPercent);
        
        if(DEBUG) {
            System.out.println("[MotorTestMappingThread] Left Percent: " + percent);
            System.out.println("[MotorTestMappingThread] Right Percent: " + secondPercent);
        }
    }
}
