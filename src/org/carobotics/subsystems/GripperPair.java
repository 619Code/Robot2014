package org.carobotics.subsystems;

import org.carobotics.hardware.CANJaguar;
import  org.carobotics.hardware.DigitalInput;

/**
 * @author CaRobotics
 */
public class GripperPair {
    protected CANJaguar leftClaw, rightClaw;
    protected DigitalInput leftLimit, rightLimit;
    private boolean leftClosed, rightClosed;
    
    // TODO: This should probably be changed to 1.0 after initial testing
    // IMPORTANT: You can't just change this without recalibrating the current
    //            constants! Leave this alone!!!
    private final static double PERCENT_VOLTAGE_SCALE = 0.5;
    
    // Current at which the gripper is probably closed
    private final static double CLOSING_TARGET_CURRENT = 10.0; //TODO: Calibrate current needed to stop
    
    public GripperPair(int leftClaw, int rightClaw, int leftLimit, int rightLimit){
        this.leftClaw = new CANJaguar(leftClaw);
        this.rightClaw = new CANJaguar(rightClaw);
        this.leftLimit = new DigitalInput(leftLimit);
        this.rightLimit = new DigitalInput(rightLimit);
        leftClosed = false;
        rightClosed = false;
    }
    
    public void close(){
        close(1 * PERCENT_VOLTAGE_SCALE);
    }
    
    public void close(double speed) {
        if(speed < 0) System.out.println("Woah! Are you sure you're not moving the gripper in the wrong direction?");
        
        leftClaw.set(speed);
        rightClaw.set(speed);
        
        
        if(Math.abs(leftClaw.getCurrent()) >= CLOSING_TARGET_CURRENT) {
            leftClaw.set(0);
            leftClosed = true;
        }
        
        if(Math.abs(rightClaw.getCurrent()) >= CLOSING_TARGET_CURRENT) {
            rightClaw.set(0);
            rightClosed = true;
        }
    }
    
    public void open(){
        open(-1 * PERCENT_VOLTAGE_SCALE);
    }
    
    public void open(double speed) {
        if(speed > 0) System.out.println("Woah! Are you sure you're not moving the gripper in the wrong direction?");
        
        leftClaw.set(speed);
        rightClaw.set(speed);
        
        if(leftLimit.get()) {
            leftClaw.set(0);
            leftClosed = false;
        } else if(rightLimit.get()) {
            rightClaw.set(0);
            leftClosed = false;
        }
    }
    
    public void stop(){
        leftClaw.set(0.0);
        rightClaw.set(0.0);
    }

    public CANJaguar getLeftClaw() {
        return leftClaw;
    }

    public CANJaguar getRightClaw() {
        return rightClaw;
    }
    
    public boolean isClosed() {
        return leftClosed && rightClosed;
    }

    public boolean isLeftClosed() {
        return leftClosed;
    }

    public boolean isRightClosed() {
        return rightClosed;
    }
    
    public boolean leftLimit(){
        return leftLimit.get();
    }
    
    public boolean rightLimit(){
        return rightLimit.get();
    }
}
