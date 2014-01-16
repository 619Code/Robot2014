package org.carobotics.hardware;

/**
 * @author CaRobotics
 */
public class Compressor {
    private edu.wpi.first.wpilibj.Compressor comp;
    
    //pressure switch is 
    public Compressor(int pressureSwitch, int compRelay){
        comp = new edu.wpi.first.wpilibj.Compressor(pressureSwitch, compRelay);
    }
    
    public Compressor(int pressureSwitchSlot, int pressureSwitchChannel, int compresssorRelaySlot, int compressorRelayChannel) {
        comp = new edu.wpi.first.wpilibj.Compressor(pressureSwitchSlot, pressureSwitchChannel, compresssorRelaySlot, compressorRelayChannel);
    }
    
    public void start(){
        comp.start();
    }
    
    public void stop () {
        comp.stop();
    }
    
    public boolean enabled (){
        return comp.enabled();
    }
    
    public boolean getPressureSwitchValue(){
        return comp.getPressureSwitchValue();
    }
    
    public void free(){
        
    }
    
    
}
