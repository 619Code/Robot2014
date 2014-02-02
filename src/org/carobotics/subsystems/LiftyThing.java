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
    protected DigitalInput limit;
    private boolean isUnlocked = true;
    public LiftyThing
        (int motor, int servo, int limit) {
        this.motor = new Talon(motor);

        this.servo = new Servo(servo);
        this.servo.setRaw(1);
        
        this.limit = new DigitalInput(limit);
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
    
    public boolean isLimit() {
        return limit.get();
    }
}
