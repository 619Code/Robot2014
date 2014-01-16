
package org.carobotics.subsystems;

import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Servo;
/**
 * Mechanum drive base that is based off the code for the tank drive base, just has extra motors and servos added
 * @author admin
 */
public class MechanumDriveBase {
    protected CANJaguar topLeftmotor, topRightmotor, bottomLeftmotor, bottomRightmotor;
    protected Servo topLeftshifter, topRightshifter, bottomLeftshifter, bottomRightshifter;
    
        public MechanumDriveBase(int topLeftmotorCANID, int topRightmotorCANID, int bottomLeftmotorCANID, int bottomRightmotorCANID){
            topLeftmotor = new CANJaguar(topLeftmotorCANID);
            topRightmotor = new CANJaguar(topRightmotorCANID);
            bottomLeftmotor = new CANJaguar(bottomLeftmotorCANID);
            bottomRightmotor = new CANJaguar(bottomRightmotorCANID);
            topLeftshifter = null;
            topRightshifter = null;
            bottomLeftshifter = null;
            bottomRightshifter = null;
    }
        public MechanumDriveBase(int topLeftmotorCANID, int topRightmotorCANID, int bottomLeftmotorCANID, int bottomRightmotorCANID,
                int digitalSidecarModule, int topLeftshifterServo, int topRightshifterServo, int bottomLeftshifterServo, int bottomRightshifterServo){
            this(topLeftmotorCANID, topRightmotorCANID, bottomLeftmotorCANID, bottomRightmotorCANID);
            topLeftshifter = new Servo(digitalSidecarModule, topLeftshifterServo);
            topRightshifter = new Servo(digitalSidecarModule, topRightshifterServo);
            bottomLeftshifter = new Servo(digitalSidecarModule, bottomLeftshifterServo);
            bottomRightshifter = new Servo(digitalSidecarModule, bottomRightshifterServo);
    }
        
        public CANJaguar getTopleftJaguar() {
            return topLeftmotor;
    }

        public CANJaguar getToprightJaguar() {
            return topRightmotor;
    }
        public CANJaguar getBottomleftJaguar() {
            return bottomLeftmotor;    
    }
        public CANJaguar getBottomrightJaguar() {
            return bottomRightmotor;    
    }
        //drive forward and bakcwards
        public void drive(double percent){
            topLeftmotor.setPercent(percent);
            topRightmotor.setPercent(percent);
            bottomLeftmotor.setPercent(percent);
            bottomRightmotor.setPercent(percent);
    }
        public void slide(double sidepercent){
            topLeftmotor.setPercent(-sidepercent);
            topRightmotor.setPercent(sidepercent);
            bottomLeftmotor.setPercent(-sidepercent);
            bottomRightmotor.setPercent(sidepercent);
    }

    
    public void shiftLow(){
        if(topLeftshifter==null||topRightshifter==null||bottomLeftshifter==null||bottomRightshifter==null){
            System.out.println("[MechanumDriveBase] No shifters!");
            return;
        }
        topLeftshifter.set(0);
        topRightshifter.set(0);
        bottomLeftshifter.set(0);
        bottomRightshifter.set(0);
               
    }
    
     public void shiftHigh(){
        if(topLeftshifter==null||topRightshifter==null||bottomLeftshifter==null||bottomRightshifter==null){
            System.out.println("[MechanumDriveBase] No shifters!");
            return;
        }
        topLeftshifter.set(1);
        topRightshifter.set(1);
        bottomLeftshifter.set(1);
        bottomRightshifter.set(1);
    }
}
