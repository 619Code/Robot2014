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
        
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_THROTTLE);
        double yRightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y);
        double xRightScalePercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_X);
        double yLeftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y);
        double xLeftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X);
        
        double leftSpeed;
        double rightSpeed;
        double backSpeed;
        
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }
        
        //working on simple equation to replace series of else-if statements above for faster and smoother driving
        leftSpeed = yLeftScalePercent;
        rightSpeed = -yRightScalePercent;
        backSpeed = xLeftScalePercent;
        
        if(xLeftScalePercent < .2 && xLeftScalePercent > -.2 && yLeftScalePercent < .2 && yLeftScalePercent > -.2 &&
                xRightScalePercent < .2 && xRightScalePercent > -.2 && yRightScalePercent < .2 && yRightScalePercent > -.2){
            driveBase.getLeftTalon().set(0);
            driveBase.getRightTalon().set(0);
            driveBase.getBackTalon().set(0);
        }else{
            driveBase.getLeftTalon().set(leftSpeed);
            driveBase.getRightTalon().set(rightSpeed);
            driveBase.getBackTalon().set(backSpeed);
        }       
        
        //        if(xLeftScalePercent < .2 && xLeftScalePercent > -.2 && yLeftScalePercent < .2 && yLeftScalePercent > -.2 &&
//                xRightScalePercent < .2 && xRightScalePercent > -.2 && yRightScalePercent < .2 && yRightScalePercent > -.2){
//            //makes robot stop if the joystick is in specified dead zone
//            driveBase.getLeftTalon().set(0);
//            driveBase.getRightTalon().set(0);
//            driveBase.getBackTalon().set(0);
//        }else if(yLeftScalePercent < -0.2 && yRightScalePercent < -0.2) {
//            //straight
//            driveBase.getLeftTalon().set(yLeftScalePercent * scalePercent);
//            driveBase.getRightTalon().set(-yRightScalePercent * scalePercent);
//            driveBase.getBackTalon().set(0.0);
//        }else if(yLeftScalePercent < -0.2){
//            //make left wheel go forward
//            driveBase.getLeftTalon().set(yLeftScalePercent * scalePercent);
//        }else if (yRightScalePercent < -0.2){
//            //make right wheel go forward
//            driveBase.getRightTalon().set(-yRightScalePercent * scalePercent);
//        }else if(xLeftScalePercent > 0.2 && xRightScalePercent > 0.2){
//            //spin right
//            driveBase.getLeftTalon().set(xLeftScalePercent * scalePercent);
//            driveBase.getRightTalon().set(xLeftScalePercent * scalePercent);
//            driveBase.getBackTalon().set(xLeftScalePercent * scalePercent);
//        }else if(xLeftScalePercent < -0.2 && xRightScalePercent < -0.2){
//            //spin left
//            driveBase.getLeftTalon().set(-xLeftScalePercent * scalePercent);
//            driveBase.getRightTalon().set(-xLeftScalePercent * scalePercent);
//            driveBase.getBackTalon().set(-xLeftScalePercent * scalePercent);
//        }
        
        if(DEBUG) {
            //System.out.println("[RobotTriangleDriveMappingThread] Y Percent: " + yScalePercent + " | X Percent: " + xScalePercent);
            System.out.println("[RobotTriangleDriveMappingThread] Left Speed: " + driveBase.getLeftTalon() + " | Right Speed: " + driveBase.getRightTalon());
        }
    }
}
