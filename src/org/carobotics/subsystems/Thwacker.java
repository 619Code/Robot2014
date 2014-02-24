 package org.carobotics.subsystems;

import org.carobotics.hardware.DualInputSolenoid;
import org.carobotics.hardware.Servo;

/**
 *
 * This class deals with the actual Thwacker object which takes in two solenoids
 * (pneumatics) to hit a 2ft. diameter ball
 * 
 * @author Student
 */
public class Thwacker {
    protected Servo camera;
    protected DualInputSolenoid shooter1, bleedAir1;
    protected DualInputSolenoid shooter2, bleedAir2;
    
    //initiates the Thwacker object variables
    public Thwacker(Servo camera, DualInputSolenoid shooter1, DualInputSolenoid shooter2, DualInputSolenoid bleedAir1, DualInputSolenoid bleedAir2){
        this.camera = camera;
        this.shooter1 = shooter1;
        this.shooter2 = shooter2;
        this.bleedAir1 = bleedAir1;
        this.bleedAir2 = bleedAir2;
    }//end Thwacker object
    
    public Thwacker(int camera,int shooter11, int shooter12, int shooter21, int shooter22, int bleedAir11, int bleedAir12, int bleedAir21, int bleedAir22){
        this.camera = new Servo(camera);
        this.shooter1 = new DualInputSolenoid(shooter11, shooter12);
        this.shooter2 = new DualInputSolenoid(shooter21, shooter22);
        this.bleedAir1 = new DualInputSolenoid(bleedAir11, bleedAir12);
        this.bleedAir2 = new DualInputSolenoid(bleedAir21, bleedAir22);
    }//end Thwacker object
    
    //checks if the thwacker stick is outside of the robot
    //or if it is set and ready to fire again
    public boolean isOut(DualInputSolenoid shooter1, DualInputSolenoid shooter2){
        
        if(shooter1.get() && shooter2.get()){
            return true;
        }else if(shooter1.get() || shooter2.get()){
            System.out.println("AHHHHHHHHHHH OH NOES!!!! THE PNEUMATICS ARE OFF!! STAHP!!!!!");
            return false;
        }//end if else
        
        return false;
        
    }//end boolean isOut
    
    //activates the solenoids to shoot
    public void reset(){
        shooter1.set(false);
        shooter2.set(false);
        bleedAir1.set(false);
        bleedAir2.set(false);
    }//end method fire
    
    //deactivates the solenoids casuing them to return
    public void fire(){
        shooter1.set(true);
        shooter2.set(true);
        bleedAir1.set(true);
        bleedAir2.set(true);
    }//end method reset
    
    //returns the camera's servo (that changes camera's angle)
    public Servo getCamera(){
        return camera;
    }//end Servo getCamera
    
    //returns one of the pneumatics of the shooter
    public DualInputSolenoid getShooter1(){
        return shooter1;
    }//end DualInputSolenoid getShooter1
    
    public DualInputSolenoid getBleedAir1(){
        return bleedAir1;
    }//end DualInputSolenoid getBleed1
    
    public DualInputSolenoid getShooter2(){
        return shooter2;
    }//end DualInputSolenoid getShooter2
    
    public DualInputSolenoid getBleedAir2(){
        return bleedAir2;
    }//end DualInputSolenoid getBleed2

}//end object Thwacker