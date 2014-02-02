package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.FrisbeeDumper;

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
    private FrisbeeDumper frisbeeDumper;
    private boolean shootersOn = true;
    
    public ThwackerMappingThread(FrisbeeDumper frisbeeDumper,
            FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.frisbeeDumper = frisbeeDumper;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        if (driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON8)) {
            double scalePercent = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Z);
            if (scalePercent < 0.3) {
                scalePercent = 0.3;
            }
            if(!frisbeeDumper.isLimit() && driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) > 0) {
                frisbeeDumper.getMotor().set(0);
            } else {
                frisbeeDumper.getMotor().set(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent);
            }
        }else{
           frisbeeDumper.getMotor().set(0);
        }
        
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON9)){
            if(shootersOn){
                frisbeeDumper.toggleLocked();
                shootersOn = false;
            }
        } else {
            shootersOn = true;
        }
    }
    
}
