package org.carobotics.subsystems.r2012;

import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Servo;

/**
 *
 * @author CaRobotics
 */
public class BallManipulator {
    public static final long LOCK_WAIT_MILLIS = 500;
    public static final long RAIL_WAIT_MILlIS = 700;
    
    private Servo leftManipulatorRailServo, rightManipulatorRailServo, leftRailLockServo, rightRailLockServo;
    private CANJaguar ballSuckerMotor;
    
    public BallManipulator(int jaguarID){
        leftManipulatorRailServo = new Servo(1,3);
        rightManipulatorRailServo = new Servo(1,4);
        leftRailLockServo = new Servo(1,5);
        rightRailLockServo = new Servo(1,6);
        ballSuckerMotor = new CANJaguar(jaguarID);
    }
    public CANJaguar getMotor(){
        return ballSuckerMotor;
    }
    
    public void lowerRails(){
        System.out.println("Lowering Rails");
        leftRailLockServo.setRaw(128);
        rightRailLockServo.setRaw(128);
        System.out.println("Lock moved");
        try {
            Thread.sleep(LOCK_WAIT_MILLIS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        leftManipulatorRailServo.setRaw(255);
        rightManipulatorRailServo.setRaw(1);
        System.out.println("Rail moved");
        try {
            Thread.sleep(RAIL_WAIT_MILlIS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        leftRailLockServo.setRaw(255);
        rightRailLockServo.setRaw(1);
        System.out.println("Lock in place");
    }
    
    public void raiseRails(){
        System.out.println("Raising Rails");
        leftRailLockServo.setRaw(128);
        rightRailLockServo.setRaw(128);
        System.out.println("Lock moved");
        try {
            Thread.sleep(LOCK_WAIT_MILLIS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        leftManipulatorRailServo.setRaw(1);
        rightManipulatorRailServo.setRaw(255);
        System.out.println("Rail moved");
    }
}
