package org.carobotics.subsystems;

import org.carobotics.hardware.SixenseController;
import org.carobotics.logic.InputManager;

/**
 * @author Gabriel Smith
 */
public class SixenseDriverStation extends DriverStation {
    public SixenseDriverStation(InputManager input) {
        leftJoystick = new SixenseController(0, input);
        rightJoystick = new SixenseController(1, input);
    }
}
