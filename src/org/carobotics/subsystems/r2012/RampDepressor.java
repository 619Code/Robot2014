package org.carobotics.subsystems.r2012;

import edu.wpi.first.wpilibj.DigitalInput;
import org.carobotics.hardware.*;

/**
 *
 * @author CaRobotics
 */
public class RampDepressor {
    
    Solenoid inSolenoid, outSolenoid;
    Relay compressor;
    DigitalInput pressureSwitch;
    
    public RampDepressor() {
        this.compressor = new Relay(1, 1);
        this.inSolenoid = new Solenoid(1, 1);
        this.outSolenoid = new Solenoid(1, 2);
        this.pressureSwitch = new DigitalInput(1, 14);
    }
    
    public void pressurize(boolean enableCompressor) {
        if(!pressureSwitch.get() && enableCompressor) {
            System.out.println("[RampDepressor] Compressor start");
            compressor.setOn();
        } else {
            System.out.println("[RampDepressor] Compressor stop");
            compressor.setOff();
        }
    }
    
    public void extendArm(){
        System.out.println("[RampDepressor] Extending arm...");
        inSolenoid.set(false);
        outSolenoid.set(true);
    }
    
    public void retractArm(){
        System.out.println("[RampDepressor] Retracting arm...");
        inSolenoid.set(true);
        outSolenoid.set(false);
    }
}
