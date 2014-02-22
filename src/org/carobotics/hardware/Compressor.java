package org.carobotics.hardware;

import org.carobotics.hardware.Relay;
import org.carobotics.hardware.DigitalInput;

/**
 * @author CaRobotics
 */
public class Compressor {
    private edu.wpi.first.wpilibj.Compressor comp;
    private DigitalInput pressureSwitch;
    private Relay relay;
    private boolean enabled = false;
    
    //pressure switch is 
    public Compressor(int pressureSwitch, int compRelay){
        //comp = new edu.wpi.first.wpilibj.Compressor(pressureSwitch, compRelay); needs to be fixed
        this.relay = new Relay(compRelay);
        this.pressureSwitch = new DigitalInput(pressureSwitch);
    }
    
    public Compressor(DigitalInput pressureSwitch, Relay compRelay){
        this.relay = compRelay;
        this.pressureSwitch = pressureSwitch;
    }//end Compressor constructor
    
    public Compressor(int pressureSwitchSlot, int pressureSwitchChannel, int compresssorRelaySlot, int compressorRelayChannel) {
        comp = new edu.wpi.first.wpilibj.Compressor(pressureSwitchSlot, pressureSwitchChannel, compresssorRelaySlot, compressorRelayChannel);
    }
    
    
    public void start(){
        
        if(!enabled){
            relay.setForward();
            enabled = true;
        }
        //comp.start();
    }
    
    public void stop() {
        relay.setOff();
        enabled = false;
    }
    
    public boolean enabled (){
        //return comp.enabled();
        return enabled;
    }
    
    public boolean getPressureSwitchValue(){
        //return comp.getPressureSwitchValue();
        return pressureSwitch.get();
    }
    
    public void free(){
        
    }
    
    
}
