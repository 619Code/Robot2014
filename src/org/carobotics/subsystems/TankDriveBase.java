package org.carobotics.subsystems;

import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Servo;
//import org.carobotics.debug.carobotics.CANJaguar;

/**
 * The world's simplest tank drive base class.
 * TODO: Should probably have higher level drive function added so the motors don't have to be controlled directly.
 * @author CaRobotics
 */
public class TankDriveBase {
    protected CANJaguar leftMotor, rightMotor;
    protected Servo leftShifter, rightShifter;
    
    public TankDriveBase(int leftMotorCANID, int rightMotorCANID){
        leftMotor = new CANJaguar(leftMotorCANID);
        rightMotor = new CANJaguar(rightMotorCANID);
        leftShifter = null;
        rightShifter = null;
    }
    
    public TankDriveBase(int leftMotorCANID, int rightMotorCANID, int digitalSidecarModule, int leftShifterServo, int rightShifterServo){
        this(leftMotorCANID, rightMotorCANID);
        leftShifter = new Servo(digitalSidecarModule, leftShifterServo);
        rightShifter = new Servo(digitalSidecarModule, rightShifterServo);
    }
    
    protected TankDriveBase(){
       // USE WITH CAUTION
       // Allows child classes to bypass creating the Jaguars
    }

    public CANJaguar getLeftJaguar() {
        return leftMotor;
    }

    public CANJaguar getRightJaguar() {
        return rightMotor;
    }
    
    public void drive(double leftPercent, double rightPercent){
        leftMotor.setPercent(leftPercent);
        rightMotor.setPercent(rightPercent);
    }
    
    public void shiftLow(){
        if(leftShifter==null||rightShifter==null){
            System.out.println("[TankDriveBase] No shifters!");
            return;
        }
        leftShifter.set(0);
        rightShifter.set(0);
    }
    
     public void shiftHigh(){
        if(leftShifter==null||rightShifter==null){
            System.out.println("[TankDriveBase] No shifters!");
            return;
        }
        leftShifter.set(1);
        rightShifter.set(1);
    }
     
     
}
