package org.carobotics.logic.r2012;

import org.carobotics.hardware.Joystick;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.r2012.FourStickDriverStation2012;
import org.carobotics.subsystems.r2012.RampDepressor;

/**
 *
 * @author CaRobotics
 */
public class RampDepressorMappingThread extends RobotThread{

    private RampDepressor rampDepressor;
    private FourStickDriverStation2012 driverStation;
    
    public RampDepressorMappingThread(RampDepressor rampDepressor, FourStickDriverStation2012 driverStation, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.rampDepressor = rampDepressor;
        this.driverStation = driverStation;
    }
    
    protected void cycle() {
        if(driverStation.getLifterJoystick().getButton(Joystick.Button.TRIGGER)){
            rampDepressor.extendArm();
        }else{
            rampDepressor.retractArm();
        }
        
        System.out.println("[RampDepressorMappingThread] Pressurize: true");
        rampDepressor.pressurize(true);
    }
    
}
