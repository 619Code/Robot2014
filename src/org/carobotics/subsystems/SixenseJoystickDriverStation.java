package org.carobotics.subsystems;

import org.carobotics.hardware.SixenseControllerJoysticks;

/**
 * @author Gabriel Smith
 */

public class SixenseJoystickDriverStation extends DriverStation{
    public SixenseJoystickDriverStation() {
        leftJoystick = new SixenseControllerJoysticks(1);
        rightJoystick = new SixenseControllerJoysticks(3);
    }
}
