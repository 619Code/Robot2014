
package org.carobotics.subsystems;

import org.carobotics.hardware.Talon;
import org.carobotics.hardware.Servo;
/**
 * Mecanum drive base that is based off the code for the tank drive base, just has extra motors and servos added
 * @author admin
 */
public class MecanumDriveBase {
    protected Talon topLeftmotor, topRightmotor, bottomLeftmotor, bottomRightmotor;
    protected Servo topLeftshifter, topRightshifter, bottomLeftshifter, bottomRightshifter;
    
        public MecanumDriveBase(int topLeftmotorChannel, int topRightmotorChannel, int bottomLeftmotorChannel, int bottomRightmotorChannel){
            topLeftmotor = new Talon(topLeftmotorChannel);
            topRightmotor = new Talon(topRightmotorChannel);
            bottomLeftmotor = new Talon(bottomLeftmotorChannel);
            bottomRightmotor = new Talon(bottomRightmotorChannel);
            topLeftshifter = null;
            topRightshifter = null;
            bottomLeftshifter = null;
            bottomRightshifter = null;
        }
        
        public MecanumDriveBase(int topLeftmotorChannel, int topRightmotorChannel, int bottomLeftmotorChannel, int bottomRightmotorChannel,
                int digitalSidecarModule, int topLeftshifterServo, int topRightshifterServo, int bottomLeftshifterServo, int bottomRightshifterServo){
            this(topLeftmotorChannel, topRightmotorChannel, bottomLeftmotorChannel, bottomRightmotorChannel);
            topLeftshifter = new Servo(digitalSidecarModule, topLeftshifterServo);
            topRightshifter = new Servo(digitalSidecarModule, topRightshifterServo);
            bottomLeftshifter = new Servo(digitalSidecarModule, bottomLeftshifterServo);
            bottomRightshifter = new Servo(digitalSidecarModule, bottomRightshifterServo);
        }
        
        public Talon getTopleftTalon() {
            return topLeftmotor;
        }

        public Talon getToprightTalon() {
            return topRightmotor;
        }
        
        public Talon getBottomleftTalon() {
            return bottomLeftmotor;    
        }
        
        public Talon getBottomrightTalon() {
            return bottomRightmotor;    
        }
        
        //drive forward and bakcwards by having all motors go in same direction
        public void drive(double percent){
            topLeftmotor.set(percent);
            topRightmotor.set(percent);
            bottomLeftmotor.set(percent);
            bottomRightmotor.set(percent);
        }
        
        //slide left to right by having front motors go in the opposite direction of the back motors
        public void slide(double sidepercent){
            topLeftmotor.set(-sidepercent);
            topRightmotor.set(-sidepercent);
            bottomLeftmotor.set(sidepercent);
            bottomRightmotor.set(sidepercent);
        }
        
        //turn left and right by having left-side motors go in opposite direction of right-side motors
        public void turn(double turnpercent){
            topLeftmotor.set(-turnpercent);
            topRightmotor.set(turnpercent);
            bottomLeftmotor.set(-turnpercent);
            bottomRightmotor.set(turnpercent);
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
