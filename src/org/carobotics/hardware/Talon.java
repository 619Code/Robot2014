package org.carobotics.hardware;

/**
 * 
 * 
 * @author CaRobotics
 */
public class Talon {
    protected  edu.wpi.first.wpilibj.Talon talon;
    protected double factor = 1.0;
    
    public Talon(int channel) {
        talon = new edu.wpi.first.wpilibj.Talon(channel);
    }
    
    public Talon(int channel, int slot) {
        talon = new edu.wpi.first.wpilibj.Talon(slot, channel);
    }
    
    /**
     * @param value Range from -1 to 1
     */
    public void set(double value) {
        talon.set(value * factor);
    }

    public double getSpeed() {
        return talon.getSpeed();
    }
    
    public void setReversed(boolean rev){
        if(rev){
            factor = -1.0;
        }else{
            factor = 1.0;
        }
    }
    
    
}
