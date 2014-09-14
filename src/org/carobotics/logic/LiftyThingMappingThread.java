/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;
import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.Talon;
import org.carobotics.subsystems.LiftyThing;
import org.carobotics.subsystems.FourStickDriverStation;


/**
 * 
 * Allow us to interact between joysticks and lifty-thingy
 * 
 * @author Student
 */
public class LiftyThingMappingThread extends RobotThread {
    
    protected FourStickDriverStation driverStation;
    private boolean topLimit;
    private boolean botLimit;
    private LiftyThing liftyThing;
    
    public LiftyThingMappingThread(LiftyThing liftyThing, FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
    }
    
    protected void cycle() {
        
        //this.topLimit = liftyThing.isLimitLeadTop();
        //this.botLimit = liftyThing.isLimitLeadBottom();
        
        double scalePercent = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Z);
            if (scalePercent < 0.3)
                scalePercent = 0.3;
        
        /*
        
        For window motor:
        
        */
        
        //as long as you are pressing the button and moving the joystick, the 
        //lift will change, the second you let go of the button the joystick 
        //will stop moving the lift
        if (driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON2)) {
            
            if(liftyThing.isLimitLiftBottom() && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) > 0) {
                liftyThing.getMotor().set(0);
                System.out.println("4");
                //liftyThing.getMotor().setReversed(true);
            } else if(liftyThing.isLimitLiftTop() && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) < 0){
                liftyThing.getMotor().set(0);
                System.out.println("3");
                //liftyThing.getMotor().setReversed(false);
            } else {
                liftyThing.getMotor().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent);
            }
        }else{
           liftyThing.getMotor().set(0);
        }//if-else
        
        /*
       
       For lead screw:
       
       */
        
//        double input = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
//        
//        Talon leadScrew = liftyThing.getLeadScrew();
//        
//        //System.out.println("input: " + input + "\ttopLmit: " + topLimit + "\tbotLimit: " + botLimit);
//        
//        if((input > 0 && !topLimit) || (input < 0 && !botLimit)) {
//            //System.out.println("first: " + (input > 0 && !topLimit) + "\tsecond: " + (input < 0 && !botLimit));
//            leadScrew.set(input);
//        } else {
//            leadScrew.set(0);
//        }
       
    }//end cycle
}//end class

