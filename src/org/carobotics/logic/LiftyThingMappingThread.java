/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.LiftyThing;
import java.util.Vector;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.hardware.Talon;
import org.carobotics.hardware.Solenoid;

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
    private Talon talon;
    private Solenoid leadTop, leadBottom, liftTop, liftBottom;
    
    public LiftyThingMappingThread(LiftyThing liftyThing, Talon leadScrew, Solenoid leadTop, Solenoid leadBottom, Solenoid liftTop, Solenoid liftBottom, FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
        this.talon = leadScrew;
    }
    
    protected void cycle() {
        
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
          if(atTop){
              talon.set(-1);
              if(){
                  
              }
          }
          
          
      }else{
          
      }
       
    }
}

