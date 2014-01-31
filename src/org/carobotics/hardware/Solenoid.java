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
    
    public boolean get(){
        return sol.get();
    }
    
    public void set(boolean on){
        sol.set(on);
        lastSetTime = System.currentTimeMillis();
    }
    
    public long getLastSetTime(){
        return lastSetTime;
    }
}
