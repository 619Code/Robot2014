package org.carobotics.subsystems;

import org.carobotics.hardware.Solenoid;

/**
 *
 * This class deals with the actual Thwacker object which takes in two solenoids
 * (pneumatics) to hit a 2ft. diameter ball
 * 
 * @author Student
 */
public class Thwacker {
    protected Solenoid shooter1, shooter2;
    
    //initiates the Thwacker object variables
    public Thwacker(Solenoid shooter1, Solenoid shooter2){
        this.shooter1 = shooter1;
        this.shooter2 = shooter2;
    }//end Thwacker object
    
    //checks if the thwacker stick is outside of the robot
    //or if it is set and ready to fire again
    public boolean isOut(Solenoid shooter){
        
        if(shooter.get()){
            return true;
        }else{
            return false;
        }//end if else
        
    }//end boolean isOut
    
    //activates the solenoids to shoot
    public void fire(){
        shooter1.set(true);
        shooter2.set(true);
    }//end method fire
    
    //deactivates the solenoids casuing them to return
    public void reset(){
        shooter1.set(false);
        shooter2.set(false);
    }//end method reset
    
    //returns one of the pneumatics of the shooter
    public Solenoid getShooter1(){
        return shooter1;
    }//end Solenoid getShooter1
    
    public Solenoid getShooter2(){
        return shooter2;
    }//end Solenoid getShooter1
    
}//end object Thwacker