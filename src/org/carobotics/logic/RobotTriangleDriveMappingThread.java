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
        double yRightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y);
        double xRightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_X);
        double yLeftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y);
        double xLeftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X);
        
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }
        
        double ySpeed = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double xSpeed = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X) * scalePercent;
        /*
        double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * rightScalePercent;
        double leftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * leftScalePercent;
         */
        if(xLeftScalePercent < .2 && xLeftScalePercent > -.2 && yLeftScalePercent < .2 && yLeftScalePercent > -.2 &&
                xRightScalePercent < .2 && xRightScalePercent > -.2 && yRightScalePercent < .2 && yRightScalePercent > -.2){
            driveBase.getLeftTalon().set(0);
            driveBase.getRightTalon().set(0);
            driveBase.getBackTalon().set(0);
        }else if(yLeftScalePercent < -0.2 && yRightScalePercent < -0.2) {
            //straight up
            driveBase.getLeftTalon().set(yLeftScalePercent * scalePercent);
            driveBase.getRightTalon().set(-yRightScalePercent * scalePercent);
            driveBase.getBackTalon().set(0.0);
            
        }else if(yLeftScalePercent < -0.2){
            driveBase.getLeftTalon().set(yLeftScalePercent * scalePercent);
        }else if (yRightScalePercent < -0.2){
            driveBase.getRightTalon().set(-yRightScalePercent * scalePercent);
        }
        
//        else if(xScalePercent > 0.2){
//            //turning left while sitting still
//            driveBase.getLeftTalon().set(-.25);
//            driveBase.getRightTalon().set(-.25);
//            driveBase.getBackTalon().set(-.25);
//        }else if(xScalePercent < -0.2){
//            //turning right while sitting still
//            driveBase.getLeftTalon().set(.25);
//            driveBase.getRightTalon().set(.25);
//            driveBase.getBackTalon().set(.25);
//        }
        
        if(DEBUG) {
            //System.out.println("[RobotTriangleDriveMappingThread] Y Percent: " + yScalePercent + " | X Percent: " + xScalePercent);
            System.out.println("[RobotTriangleDriveMappingThread] Left Speed: " + driveBase.getLeftTalon() + " | Right Speed: " + driveBase.getRightTalon());
        }
    }
}
