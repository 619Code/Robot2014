/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carobotics.logic;

import org.carobotics.hardware.Talon;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.MecanumDriveBase;

/**
 *
 * @author caadmin
 */
public class MecanumDriveMappingThread extends RobotThread {
    protected MecanumDriveBase mecanumDriveBase;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = false;
    private long lastTime = 0;
    
    Talon topLefttalon, topRighttalon, bottomLefttalon, bottomRighttalon;

    public MecanumDriveMappingThread(MecanumDriveBase mecanumDriveBase,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.mecanumDriveBase = mecanumDriveBase;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        
        ///System.out.println(System.currentTimeMillis()-lastTime);
        ///lastTime = System.currentTimeMillis();
        
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);  //fix
        if(scalePercent < 0.3) scalePercent = 0.3;
        
        //gets percentages (numbers from -1 to 1) from the joystick's axes used for driving
        double percent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double sidepercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X) * scalePercent;
        double turnpercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_TWIST) * scalePercent;
        
        
        double topLeftpercent = percent - sidepercent - turnpercent;
        double topRightpercent = percent - sidepercent + turnpercent;
        double bottomLeftpercent = percent + sidepercent - turnpercent;
        double bottomRightpercent = percent + sidepercent + turnpercent;
        
        try {
            mecanumDriveBase.getTopleftTalon().set(topLeftpercent);
            mecanumDriveBase.getToprightTalon().set(topRightpercent);
            mecanumDriveBase.getBottomleftTalon().set(bottomLeftpercent);
            mecanumDriveBase.getBottomrightTalon().set(bottomRightpercent);
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
            mecanumDriveBase.shiftHigh();
        }
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)){
            System.out.println("[MechanumDriveMappingThread] Shifting Low");
            mecanumDriveBase.shiftLow();
        }
    }
}
