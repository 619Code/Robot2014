/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.RobotTriangleBase;
import org.carobotics.hardware.Talon;

/**
 *
 * @author Student
 */
public class RobotTriangleDriveMappingThread extends RobotThread {
    
    protected RobotTriangleBase driveBase;
    protected Talon leftTalon, rightTalon, backTalon;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;

    public RobotTriangleDriveMappingThread(RobotTriangleBase driveBase,DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.driveBase = driveBase;
        this.driverStation = driverStation;
    }
    
    protected void cycle() {
        /*
        double rightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Z);
        double leftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
        */
        
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
        double yScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y);
        double xScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X);
        
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }
        
        double ySpeed = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double xSpeed = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X) * scalePercent;
        /*
        double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * rightScalePercent;
        double leftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * leftScalePercent;
         */
        
        if(yScalePercent > 0 && (xScalePercent >= -.2 && xScalePercent <= -.2)) {
            //straight up
            driveBase.getLeftTalon().set(ySpeed);
            driveBase.getRightTalon().set(ySpeed);
            driveBase.getBackTalon().set(0);
        } else if (yScalePercent > .2 && (xScalePercent < -.2 && xScalePercent >= -1)) {
            //left up
            driveBase.getLeftTalon().set(ySpeed);
            driveBase.getRightTalon().set(ySpeed /xSpeed);
            driveBase.getBackTalon().set(xSpeed);
        } else if (yScalePercent > .2 && (xScalePercent > .2 && xScalePercent <= 1)) {
            //right up
            driveBase.getLeftTalon().set(ySpeed);
            driveBase.getRightTalon().set(ySpeed /xSpeed);
            driveBase.getBackTalon().set(xSpeed);
        } else if (xScalePercent <= -.2 && (yScalePercent <= .2 && yScalePercent >= -.2)) {
            //straight left
            driveBase.getLeftTalon().set(xSpeed);
            driveBase.getRightTalon().set(xSpeed);
            driveBase.getBackTalon().set(xSpeed);
        } else if (xScalePercent >= .2 && (yScalePercent <= .2 && yScalePercent >= -.2)) {
            //straight right
            driveBase.getLeftTalon().set(xSpeed);
            driveBase.getRightTalon().set(xSpeed);
            driveBase.getBackTalon().set(xSpeed);
        } else {
        
        }
       
        if(DEBUG) {
            System.out.println("[RobotTriangleDriveMappingThread] Y Percent: " + yScalePercent + " | X Percent: " + xScalePercent);
        }
    }
}
