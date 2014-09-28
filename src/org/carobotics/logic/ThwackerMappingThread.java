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
        
        double scalePercent = driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Z);
            if (scalePercent < 0.3)
                scalePercent = 0.3;
        
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.TRIGGER)){
            if(shootersReady){
                thwacker.fire();
                shootersReady = false;
            }
        } 
        else {
            shootersReady = true;
        }
        
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON1)){
            thwacker.reset();
            shootersReady = true;
        }//end if
        
    }
    
}
