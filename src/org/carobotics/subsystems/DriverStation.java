package org.carobotics.subsystems;

import org.carobotics.hardware.Joystick;

/**
 *
 * @author CaRobotics
 */
public class DriverStation {
    protected Joystick leftJoystick, rightJoystick;

    public DriverStation(){
        leftJoystick = new Joystick(1);
        rightJoystick = new Joystick(2);
    }

    public DriverStation(int leftJoystickID, int rightJoystickID){
        leftJoystick = new Joystick(leftJoystickID);
        rightJoystick = new Joystick(rightJoystickID);
    }

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }
}
