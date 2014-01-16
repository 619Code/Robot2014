package org.carobotics.subsystems.r2012;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;

/**
 *
 * @author CaRobotics
 */
public class FourStickDriverStation2012 extends DriverStation{
    private Joystick lifterJoystick, ballManipulatorJoystick;
    
    public FourStickDriverStation2012(int leftId, int rightId, int lifterId, int ballManipulatorId){
        super(leftId, rightId);
        lifterJoystick = new Joystick(lifterId);
        ballManipulatorJoystick = new Joystick(ballManipulatorId);
    }

    public Joystick getBallManipulatorJoystick() {
        return ballManipulatorJoystick;
    }

    public Joystick getLifterJoystick() {
        return lifterJoystick;
    }
    
    
}
