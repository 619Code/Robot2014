package org.carobotics.hardware;

/**
 * @author CaRobotics
 * not tested yet!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class MotorController29 {
    private  edu.wpi.first.wpilibj.Talon talon;
    
    public MotorController29(int channel) {
        talon = new edu.wpi.first.wpilibj.Talon(channel);
    }
    
    public MotorController29(int channel, int slot) {
        talon = new edu.wpi.first.wpilibj.Talon(slot, channel);
    }
    
    /**
     * @param value Range from -1 to 1
     */
    public void set(double value) {
        talon.set(value);
    }

    public double getSpeed() {
        return talon.getSpeed();
    }
}
