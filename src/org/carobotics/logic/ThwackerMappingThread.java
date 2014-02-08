package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.Thwacker;

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
    private boolean shootersOn = true;
    
    public ThwackerMappingThread(Thwacker thwacker,
            FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.thwacker = thwacker;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.TRIGGER)){
            if(shootersOn){
                thwacker.fire();
                shootersOn = false;
            }
        } else {
            //if pneumatics are messing up and not working then delete thwacker.reset();
            thwacker.reset();
            shootersOn = true;
        }
        
        if(driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON2)){
            thwacker.reset();
        }//end if
        
    }
    
}
