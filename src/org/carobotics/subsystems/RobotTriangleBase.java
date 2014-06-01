/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;

import org.carobotics.hardware.Talon;
//import org.carobotics.subsystems.DriverStation;


/**
 *
 * @author Student
 */
public class RobotTriangleBase {
    private Talon leftMotor , rightMotor, backMotor;
    
    public RobotTriangleBase(int leftMotorTalonID, int rightMotorTalonID, int backMotorTalonID){
        leftMotor = new Talon(leftMotorTalonID);
        rightMotor = new Talon(rightMotorTalonID);
        backMotor = new Talon(backMotorTalonID);
    }//end constructor TalonDriveBase
    
    public RobotTriangleBase(Talon leftMotor, Talon rightMotor, Talon backMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.backMotor = backMotor;
    }
    
    public Talon getLeftTalon() {
        return leftMotor;
    }

    public Talon getRightTalon() {
        return rightMotor;
    }
    
    public Talon getBackTalon() {
        return backMotor;
    }
    
    public void drive(double leftPercent, double rightPercent, double backPercent) {
        leftMotor.set(leftPercent);
        rightMotor.set(rightPercent);
        backMotor.set(backPercent);
    }
}
