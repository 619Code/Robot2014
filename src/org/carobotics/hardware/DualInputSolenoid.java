package org.carobotics.hardware;

/**
 * @author carobotics
 */
public class DualInputSolenoid {
    
    private edu.wpi.first.wpilibj.Solenoid solLeft, solRight;
    
    private long lastSetTime;
    
    public DualInputSolenoid(int id1, int id2){
        solLeft = new edu.wpi.first.wpilibj.Solenoid(id1);
        solRight = new edu.wpi.first.wpilibj.Solenoid(id2);
        lastSetTime = System.currentTimeMillis();
    }
    
    public DualInputSolenoid(int module, int channel1, int channel2){
        solLeft = new edu.wpi.first.wpilibj.Solenoid(module, channel1);
        solRight = new edu.wpi.first.wpilibj.Solenoid(module, channel2);
        lastSetTime = System.currentTimeMillis();
    }
    
    public boolean get(){
        return solLeft.get();
    }
    
    public void set(boolean on){
        solLeft.set(on);
        solRight.set(!on);
        lastSetTime = System.currentTimeMillis();
    }
    
    public long getLastSetTime(){
        return lastSetTime;
    }
}
