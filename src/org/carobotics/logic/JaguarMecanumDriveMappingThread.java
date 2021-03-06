/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.Talon;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.JaguarMecanumDriveBase;

/**
 *
 * deals with the joysticks interacting with the mecanum wheels, the alorithms
 * 
 * @author Student
 */
public class JaguarMecanumDriveMappingThread extends RobotThread{
    protected JaguarMecanumDriveBase mecanumDriveBase;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;
    
    Talon topLefttalon, topRighttalon, bottomLefttalon, bottomRighttalon;

    public JaguarMecanumDriveMappingThread(JaguarMecanumDriveBase mecanumDriveBase,
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
        double turnpercent = 0;//driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_TWIST) * scalePercent;
        
        
        //needs to be fixed, as soon as joysticks are pushed in any direction, wheels don't stop even when not touching joysticks
        
        double topLeftpercent = -(percent - sidepercent - turnpercent);
        double topRightpercent = percent - sidepercent + turnpercent;
        double bottomLeftpercent = -(percent + sidepercent - turnpercent);
        double bottomRightpercent = percent + sidepercent + turnpercent;
        
        try {
            mecanumDriveBase.getTopleftJaguar().set(topLeftpercent);
            mecanumDriveBase.getToprightJaguar().set(topRightpercent);
            mecanumDriveBase.getBottomleftJaguar().set(bottomLeftpercent);
            mecanumDriveBase.getBottomrightJaguar().set(bottomRightpercent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
        
        if(DEBUG) 
            
            System.out.println("[JaguarMechanumDriveMappingThread] percent: "+ percent);
            System.out.println("[JaguarMechanumDriveMappingThread] sidepercent: "+ sidepercent);
            System.out.println("[JaguarMechanumDriveMappingThread] turnpercent: "+ turnpercent);
            
        
        shift();
    }
    
    protected void shift(){
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON11)){
            System.out.println("[JaguarMechanumDriveMappingThread] Shifting High");
            mecanumDriveBase.shiftHigh();
        }
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)){
            System.out.println("[JaguarMechanumDriveMappingThread] Shifting Low");
            mecanumDriveBase.shiftLow();
        }
    }
}
