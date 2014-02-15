/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;

import org.carobotics.hardware.Talon;
import org.carobotics.hardware.Solenoid;
import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.LiftyThing;
import org.carobotics.subsystems.FourStickDriverStation;
import java.util.Vector;


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
    private Talon leadScrew;
    private DigitalInput leadTop, leadBottom, liftTop, liftBottom;
    
    public LiftyThingMappingThread(LiftyThing liftyThing, Talon leadScrew, DigitalInput leadTop, DigitalInput leadBottom, DigitalInput liftTop, DigitalInput liftBottom, FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
        this.leadScrew = leadScrew;
        this.leadTop = leadTop;
        this.leadBottom = leadBottom;
        this.liftTop = liftTop;
        this.liftBottom = liftBottom;
    }
    
    protected void cycle() {
        
        if(liftBottom.get()){
            System.out.println("LIMIT SWITCH WORKS MUDDER FUCKER!!!");
        }//end if
        
       if (driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON2)) {
            double scalePercent = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Z);
            if (scalePercent < 0.3) {
                scalePercent = 0.3;
            }
            if(!liftyThing.isLimit() && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) > 0) {
                liftyThing.getMotor().set(0);
            } else {
                liftyThing.getMotor().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent);
            }
        }else{
           liftyThing.getMotor().set(0);
        }
       
      if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON7)){
          //if lift mechanism is at top
          if(atTop){
              //reverse the talon to go down
              leadScrew.set(-1);
              //if the lift mechanism hits the bottom limit switch
              if(leadBottom.get()){
                  //stop the talon
                  leadScrew.set(0);
                  atTop = false;//change the variable to say the mechanism is at bottom
              }//end if
          //otherwise if the lift mechanism is at bottom
          }else{
              //make talon go up
              leadScrew.set(1);
              //if the lift mechanism hits the top limit swtich
              if(leadTop.get()){
                  //stop the talon
                  leadScrew.set(0);
                  atTop = true;//change the variable to say the mechanism is at top
              }//end if
          }//end if-else   
      }else{
          //have the talon normally stay in position
          leadScrew.set(0);
      }
       
    }
}

