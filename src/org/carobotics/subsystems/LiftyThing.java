/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.hardware.Servo;
import org.carobotics.hardware.Talon;

/*
 * @author CaRobotics
 */
public class LiftyThing {
    protected Servo servo;
    protected Talon motor;
    //protected Talon leadScrewmotor;
    protected DigitalInput leadTop, leadBottom;
    protected DigitalInput liftTop, liftBottom;
    private boolean isUnlocked = true;
    
    public LiftyThing(int motor, int servo, /*int leadScrewmotor,*/ int leadTop, int leadBottom, int liftTop, int liftBottom) {
        this.motor = new Talon(motor);
        this.servo = new Servo(servo);
        this.servo.setRaw(1);
        //this.leadScrewmotor = new Talon(leadScrewmotor);
        this.leadTop = new DigitalInput(leadTop);
        this.leadBottom = new DigitalInput(leadBottom);
        this.liftTop = new DigitalInput(leadTop);
        this.liftBottom = new DigitalInput(leadTop);
    }

    public void lock() {
        servo.setRaw(1);
        isUnlocked = false;
        
    }

    public void unlock() {
        servo.setRaw(127);
        isUnlocked = true;
    }
    public void toggleLocked(){
        if(isUnlocked){
            lock();
        }else{
            unlock();
        }
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public Talon getMotor() {
        return motor;
    }
    
    public boolean isLimitLeadTop() {
        return leadTop.get();
    }
    
    public boolean isLimitLeadBottom() {
        return leadBottom.get();
    }
    
    public boolean isLimitLiftTop() {
        return liftTop.get();
    }
    
    public boolean isLimitLiftBottom() {
        return liftBottom.get();
    }
}
