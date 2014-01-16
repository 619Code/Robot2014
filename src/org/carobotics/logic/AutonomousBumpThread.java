package org.carobotics.logic;

import org.carobotics.subsystems.DriverStation;
import org.carobotics.hardware.Talon;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class AutonomousBumpThread extends RobotThread {
    protected Talon leftTalon;
    protected Talon rightTalon;
    protected Talon leftTalon2, rightTalon2;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long beginTime = 0;

    public AutonomousBumpThread(Talon leftTalon, Talon rightTalon, Talon leftTalon2, Talon rightTalon2,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.leftTalon = leftTalon;
        this.rightTalon = rightTalon;
        this.driverStation = driverStation;
        this.leftTalon2 = leftTalon2;
        this.rightTalon2 = rightTalon2;
    }

    protected void begin() {
        beginTime = System.currentTimeMillis();
    }
    
    protected void cycle() {
        long timeDiff = System.currentTimeMillis() - beginTime;
        if(timeDiff < 4000){
            setSpeedAll((timeDiff/4000) * 0.5);
        }else{
            setSpeedAll(0.0); // stop all
        }
    }
    
    private void setSpeedAll(double speed){
        leftTalon.set(speed);
        rightTalon.set(speed);
        leftTalon2.set(speed);
        rightTalon2.set(speed);
    }
}
