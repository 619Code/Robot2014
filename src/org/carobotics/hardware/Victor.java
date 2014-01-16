package org.carobotics.hardware;

/**
 * 
 * 
 * @author CaRobotics
 */
public class Victor {
    protected  edu.wpi.first.wpilibj.Victor victor;
    protected double factor = 1.0;
    
    public Victor(int channel) {
        victor = new edu.wpi.first.wpilibj.Victor(channel);
    }
    
    public Victor(int channel, int slot) {
        victor = new edu.wpi.first.wpilibj.Victor(slot, channel);
    }
    
    /**
     * @param value Range from -1 to 1
     */
    public void set(double value) {
        victor.set(value * factor);
    }

    public double getSpeed() {
        return victor.getSpeed();
    }
    
    public void setReversed(boolean rev){
        if(rev){
            factor = -1.0;
        }else{
            factor = 1.0;
        }
    } 
}