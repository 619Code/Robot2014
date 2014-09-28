/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.hardware.DualInputSolenoid;
import org.carobotics.hardware.Talon;

/*
 * @author CaRobotics
 */
public class LiftyThing {
    protected Talon motor, motor2;
    protected Talon leadScrew;
    protected DigitalInput leadTop, leadBottom;
    protected DigitalInput liftTop, liftTop2, liftBottom, liftBottom2;
    protected DualInputSolenoid tertiaryMechanism;
    private boolean isUnlocked = true;
    
    public LiftyThing(int motor, int leadScrew, int leadTop, int leadBottom, int liftTop, int liftBottom) {
        this.motor = new Talon(motor);
        this.leadScrew = new Talon(leadScrew);
        this.leadTop = new DigitalInput(leadTop);
        this.leadBottom = new DigitalInput(leadBottom);
        this.liftTop = new DigitalInput(leadTop);
        this.liftBottom = new DigitalInput(leadTop);
    }
    
    public LiftyThing(Talon motor, Talon leadScrew, DigitalInput leadTop, DigitalInput leadBottom, DigitalInput liftTop, DigitalInput liftBottom){
        this.motor = motor;
        this.leadScrew = leadScrew;
        this.leadTop = leadTop;
        this.leadBottom = leadBottom;
        this.liftTop = liftTop;
        this.liftBottom = liftBottom;
    }//end LiftyThing constructor
    
    public LiftyThing(Talon motor, DigitalInput liftTop, DigitalInput liftBottom){
        this.motor = motor;
        this.liftTop = liftTop;
        this.liftBottom = liftBottom;
    }//end LiftyThing constructor
    
    public LiftyThing(Talon motor, DigitalInput liftTop, DigitalInput liftBottom, Talon motor2, DigitalInput liftTop2, DigitalInput liftBottom2, DualInputSolenoid tertiaryMechanism){
        this.motor = motor;
        this.liftTop = liftTop;
        this.liftBottom = liftBottom;
        this.motor2 = motor2;
        this.liftTop2 = liftTop2;
        this.liftBottom2 = liftBottom2;
        this.tertiaryMechanism = tertiaryMechanism;
    }//end constructor

    public Talon getMotor() {
        return motor;
    }
    
    public Talon getMotor2(){
        return motor2;
    }//end method getMotor2
    
    public void setTertiaryMechanism(boolean set){
        tertiaryMechanism.set(set);
    }//end method setTeritaryMechanism
    
    public boolean isOutTertiaryMechanism(){
        return tertiaryMechanism.get();
    }//end 
    
    public Talon getLeadScrew(){
        return leadScrew;
    }//end Talon getLeadScrew
    
    public boolean isLimitLeadTop() {
        return leadTop.get();
    }
    
    public boolean isLimitLeadBottom() {
        return leadBottom.get();
    }
    
    public boolean isLimitLiftTop() {
        return liftTop.get();
    }
    
    public boolean isLimitLiftBottom() {
        return liftBottom.get();
    }
    
    public boolean isLimitLiftTop2(){
        return liftTop2.get();
    }//end boolean isLimitLiftTop2
    
    public boolean isLimitLiftBottom2(){
        return liftBottom2.get();
    }//end boolean isLimitLiftBottom2
}
