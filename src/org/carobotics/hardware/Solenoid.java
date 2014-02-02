package org.carobotics.hardware;

/**
 * @author carobotics
 */
public class Solenoid {
    
    private edu.wpi.first.wpilibj.Solenoid sol;
    
    private long lastSetTime;
    
    public Solenoid(int id){
        sol = new edu.wpi.first.wpilibj.Solenoid(id);
        lastSetTime = System.currentTimeMillis();
    }
    
    public Solenoid(int module, int channel){
        sol = new edu.wpi.first.wpilibj.Solenoid(module, channel);
        lastSetTime = System.currentTimeMillis();
    }
    
    //gets whether Solenoid is set on or off
    public boolean get(){
        return sol.get();
    }
    
    //set the solenoid on or off
    public void set(boolean on){
        sol.set(on);
        lastSetTime = System.currentTimeMillis();
    }
    
    //keeps track of last time the solenoid was turned on
    //(for knowing if pneumatic is filled with air again or not)
    public long getLastSetTime(){
        return lastSetTime;
    }
}
