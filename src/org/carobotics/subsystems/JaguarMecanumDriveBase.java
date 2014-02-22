/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;

import org.carobotics.hardware.Servo;
import org.carobotics.hardware.CANJaguar;

/**
 *
 * Deals with mecanum wheels and jaguars (talons are preferred)
 * 
 * @author Student
 */
public class JaguarMecanumDriveBase {
    protected CANJaguar topLeftmotor, topRightmotor, bottomLeftmotor, bottomRightmotor;
    protected Servo topLeftshifter, topRightshifter, bottomLeftshifter, bottomRightshifter;
    
        public JaguarMecanumDriveBase(int topLeftmotorChannel, int topRightmotorChannel, int bottomLeftmotorChannel, int bottomRightmotorChannel){
            topLeftmotor = new CANJaguar(topLeftmotorChannel);
            topRightmotor = new CANJaguar(topRightmotorChannel);
            bottomLeftmotor = new CANJaguar(bottomLeftmotorChannel);
            bottomRightmotor = new CANJaguar(bottomRightmotorChannel);
            topLeftshifter = null;
            topRightshifter = null;
            bottomLeftshifter = null;
            bottomRightshifter = null;
        }
        
        public JaguarMecanumDriveBase(int topLeftmotorChannel, int topRightmotorChannel, int bottomLeftmotorChannel, int bottomRightmotorChannel,
                int digitalSidecarModule, int topLeftshifterServo, int topRightshifterServo, int bottomLeftshifterServo, int bottomRightshifterServo){
            this(topLeftmotorChannel, topRightmotorChannel, bottomLeftmotorChannel, bottomRightmotorChannel);
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
                System.out.println("[JaguarMechanumDriveBase] No shifters!");
                return;
            }
            topLeftshifter.set(0);
            topRightshifter.set(0);
            bottomLeftshifter.set(0);
            bottomRightshifter.set(0);

        }
    
        public void shiftHigh(){
           if(topLeftshifter==null||topRightshifter==null||bottomLeftshifter==null||bottomRightshifter==null){
               System.out.println("[JaguarMechanumDriveBase] No shifters!");
               return;
           }
           topLeftshifter.set(1);
           topRightshifter.set(1);
           bottomLeftshifter.set(1);
           bottomRightshifter.set(1);
       }
}
