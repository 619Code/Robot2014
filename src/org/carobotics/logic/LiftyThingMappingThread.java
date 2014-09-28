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
    private boolean primaryAtTop, primaryAtBottom;
    private boolean secondaryAtTop, secondaryAtBottom;
    private boolean tertiaryOut, tertiaryIn;
    private LiftyThing liftyThing;
    
    public LiftyThingMappingThread(LiftyThing liftyThing, FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
    }
    
    protected void cycle() {
        
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON1)){
            System.out.println("Button Pressed");
            
            if(primaryAtTop){
                System.out.println("Primary mechanism is already at the top");
            }else{
                while(!liftyThing.isLimitLiftTop()){
                    liftyThing.getMotor().set(.25);
                }//end while
                primaryAtTop = true;
                primaryAtBottom = false;
            }//end if:else
            
            if(secondaryAtTop){
                System.out.println("Secondary mechanism is already at the top");
            }else{
                while(!liftyThing.isLimitLiftTop2()){
                    liftyThing.getMotor2().set(.25);
                }//end while
                secondaryAtTop = true;
                secondaryAtBottom = false;
            }//end if:else
            
            if(tertiaryOut){
                System.out.println("Tertiary mechanism is already out");
            }else{
                liftyThing.setTertiaryMechanism(true);
                tertiaryOut = true;
                tertiaryIn = false;
            }//end if:else
                
        }//end if
       
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.TRIGGER)){
            System.out.println("Trigger pulled");
            
            if(tertiaryIn){
                System.out.println("Tertiary mechanism is already in");
            }else{
                liftyThing.setTertiaryMechanism(false);
                tertiaryOut = false;
                tertiaryIn = true;
            }//end if:else
            
            if(secondaryAtBottom){
                System.out.println("Secondary mechanism is already at the bottom");
            }else{
                while(!liftyThing.isLimitLiftBottom2()){
                    liftyThing.getMotor2().set(-.25);
                }//end while
            }//end if:else
            
            if(primaryAtBottom){
                System.out.println("Primary mechanism is already at the bottom");
            }else{
                while(!liftyThing.isLimitLiftBottom()){
                    liftyThing.getMotor().set(-.25);
                }//end while
            }//end if:else
            
        }//end if
        
    }//end cycle
}//end class

