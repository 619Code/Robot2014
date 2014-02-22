package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.Thwacker;
import org.carobotics.hardware.Compressor;

/**
 *
 * This program is meant to map the thwacker action to the joysticks for the shooting of the ball 
 * 
 * 
 * @author Student
 */
public class ThwackerMappingThread extends RobotThread{
    
    protected FourStickDriverStation driverStation;
    private final static boolean DEBUG = false;
    private Thwacker thwacker;
    private Compressor comp;
    private boolean shootersReady = true;
    
    public ThwackerMappingThread(Thwacker thwacker, Compressor comp,
            FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.thwacker = thwacker;
        this.comp = comp;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.TRIGGER)){
            if(shootersReady){
                thwacker.fire();
                shootersReady = false;
            }
        } 
        else {
            //if pneumatics are messing up and not working then delete thwacker.reset();
            thwacker.reset();
            shootersReady = true;
        }
        
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON2)){
            thwacker.reset();
            shootersReady = true;
        }//end if
        
    }
    
}
