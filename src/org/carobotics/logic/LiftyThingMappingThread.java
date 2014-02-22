/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
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
    private boolean atTop = false;
    private LiftyThing liftyThing;
    
    public LiftyThingMappingThread(LiftyThing liftyThing, FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
    }
    
    protected void cycle() {
        
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
            } else if(liftyThing.isLimitLiftTop() && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) < 0){
                liftyThing.getMotor().set(0);
            } else {
                liftyThing.getMotor().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent);
            }
        }else{
           liftyThing.getMotor().set(0);
        }
       
       /*
       
       For lead screw:
       
       */
       
       liftyThing.getLeadScrew().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y)*scalePercent);
       
       if(liftyThing.isLimitLeadTop()){
           atTop = true;
           liftyThing.getLeadScrew().setReversed(true);
           System.out.println("Reached the top");
       }//end if
       
       if(liftyThing.isLimitLeadBottom()){
           atTop = false;
           liftyThing.getLeadScrew().setReversed(false);
           System.out.println("Reached the bottom");
       }//end if
       
//        //if lift mechanism is at top
//        if(atTop && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) < 0){
//              //reverse the talon to go down
//              liftyThing.getLeadScrew().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y)*scalePercent);
//              //if the lift mechanism hits the bottom limit switch
//              if(liftyThing.isLimitLeadBottom()){
//                  //stop the talon
//                  liftyThing.getLeadScrew().set(0);
//                  atTop = false;//change the variable to say the mechanism is at bottom
//                  System.out.println("Reached the bottom");
//              }//end if
//        //otherwise if the lift mechanism is at bottom
//        }else{
//              //make talon go up
//              liftyThing.getLeadScrew().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y)*scalePercent);
//              //if the lift mechanism hits the top limit swtich
//              if(liftyThing.isLimitLeadTop()){
//                  //stop the talon
//                  liftyThing.getLeadScrew().set(0);
//                  atTop = true;//change the variable to say the mechanism is at top
//                  System.out.println("Reached the top");
//              }//end if
//        }//end if-else
      
      //pulling the trigger stops the lead screw as long as the trigger is pulled
      if(driverStation.getFourthJoystick().getButton(Joystick.Button.TRIGGER)){
          liftyThing.getLeadScrew().set(0);
      }//end if
       
    }
}

