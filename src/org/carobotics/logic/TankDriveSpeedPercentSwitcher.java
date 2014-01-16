package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.TankDriveBase;

/**
 * @author CaRobotics
 */
public class TankDriveSpeedPercentSwitcher extends RobotThread{
    private TankDriveBase driveBase;
    private DriverStation driverStation;
    private double maxRPM;
    private int drivePeriod;
    private ThreadManager threadManager;
    private boolean inPercent = true;
    private TankDriveMappingThread percentThread;
    private TankDriveSpeedMappingThread speedThread;
    
    public TankDriveSpeedPercentSwitcher(TankDriveBase driveBase, DriverStation driverStation, double maxRPM, int drivePeriod, int thisThreadPeriod, ThreadManager threadManager){
        super(thisThreadPeriod, threadManager);
        this.driveBase = driveBase;
        this.driverStation = driverStation;
        this.maxRPM = maxRPM;
        this.drivePeriod = drivePeriod;
        this.threadManager = threadManager;
    }

    protected void begin(){
        percentThread = new TankDriveMappingThread(driveBase, driverStation, drivePeriod, threadManager);
        speedThread = new TankDriveSpeedMappingThread(driveBase, driverStation, maxRPM, drivePeriod, threadManager);
        speedThread.start();
        speedThread.setSuspended(true);
        percentThread.start();
    }
    
    protected void cycle() {
        
        boolean percentButton = driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON8);
        boolean speedButton = driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON9);
        if(percentButton){ // Note that this means the percent button overrides the speed button if both are pressed
            if(!inPercent){
                speedThread.setSuspended(true);
                percentThread.setSuspended(false);
                inPercent = true;
            }
        }else if(speedButton){
            if(inPercent){
                percentThread.setSuspended(true);
                speedThread.setSuspended(false);
                inPercent = false;
            }
        }
    }
    
    
}
