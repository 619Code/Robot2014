/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.MechanumDriveBase;

/**
 *
 * @author caadmin
 */
public class MechanumDriveMappingThread extends RobotThread {
    protected MechanumDriveBase mechanumDriveBase;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = false;
    private long lastTime = 0;

    public MechanumDriveMappingThread(MechanumDriveBase mechanumDriveBase,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.mechanumDriveBase = mechanumDriveBase;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        
        ///System.out.println(System.currentTimeMillis()-lastTime);
        ///lastTime = System.currentTimeMillis();
        
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);  //fix
        if(scalePercent < 0.3) scalePercent = 0.3;
        
        double percent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double sidepercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X) * scalePercent;
        
        
        try {
            mechanumDriveBase.getTopleftJaguar().setPercent(percent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
        
        try {
            mechanumDriveBase.getToprightJaguar().setPercent(percent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
                
        try {
            mechanumDriveBase.getBottomleftJaguar().setPercent(percent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
                        
        try {
            mechanumDriveBase.getBottomrightJaguar().setPercent(percent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
        
        if(DEBUG) 
            
            System.out.println("[MechanumDriveMappingThread] Percent: "+percent);
        
        shift();
    }
    
    protected void shift(){
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON11)){
            System.out.println("[MechanumDriveMappingThread] Shifting High");
            mechanumDriveBase.shiftHigh();
        }
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)){
            System.out.println("[MechanumDriveMappingThread] Shifting Low");
            mechanumDriveBase.shiftLow();
        }
    }
}
