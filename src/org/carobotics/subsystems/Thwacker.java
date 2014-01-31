/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;

import org.carobotics.hardware.Solenoid;

/**
 *
 * @author Student
 */
public class Thwacker {
    protected Solenoid shooter1, shooter2;
    
    public Thwacker(Solenoid shooter1, Solenoid shooter2){
        this.shooter1 = shooter1;
        this.shooter2 = shooter2;
    }//end Thwacker object
    
    public boolean isOut(Solenoid shooter){
        if(shooter.get() = ){
            
        }//end if
        return shooter;
    }//end boolean isOut
    
    public Solenoid getShooter1(){
        return shooter1;
    }//end Solenoid getShooter1
    
    public Solenoid getShooter2(){
        return shooter2;
    }//end Solenoid getShooter1
    
}