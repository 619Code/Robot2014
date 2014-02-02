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

/**
 * 
 * Allow us to interact between joysticks and lifty-thingy
 * 
 * @author Student
 */
public class LiftyThingMappingThread extends RobotThread {
    
    protected FourStickDriverStation driverStation;
    private LiftyThing liftyThing;
    
    public LiftyThingMappingThread(LiftyThing liftyThing,  FourStickDriverStation driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.driverStation = driverStation;
        this.liftyThing = liftyThing;
    }
    
    protected void cycle() {
       
    }
}

