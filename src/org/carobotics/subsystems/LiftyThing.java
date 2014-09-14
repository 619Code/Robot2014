/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.hardware.Talon;

/*
 * @author CaRobotics
 */
public class LiftyThing {
    protected Talon motor;
    protected Talon leadScrew;
    protected DigitalInput leadTop, leadBottom;
    protected DigitalInput liftTop, liftBottom;
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

    public Talon getMotor() {
        return motor;
    }
    
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
}
