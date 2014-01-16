package org.carobotics.hardware;

/**
 * @author CaRobotics
 */
public class DigitalInput {
    private edu.wpi.first.wpilibj.DigitalInput input;
    
    public DigitalInput(int channel) {
        input = new edu.wpi.first.wpilibj.DigitalInput(channel);
    }
    
    public DigitalInput(int channel, int module) {
        input = new edu.wpi.first.wpilibj.DigitalInput(module, channel);
    }
    
    public boolean get() {
        return input.get();
    }
}
