package org.carobotics.hardware;

import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

/**
 * @author CaRobotics
 */
public class Relay {
    private edu.wpi.first.wpilibj.Relay relay;
    
    public Relay(int modulenumber, int channel){
        relay = new edu.wpi.first.wpilibj.Relay(modulenumber, channel);
        relay.setDirection(Direction.kBoth);
    }
    
    public void setForward(){
        relay.set(Value.kForward);
    }
    
    public void setReverse(){
        relay.set(Value.kReverse);
    }
    
    public void setOn(){
        relay.set(Value.kOn);
    }
    
    public void setOff(){
        relay.set(Value.kOff);
    }
}
