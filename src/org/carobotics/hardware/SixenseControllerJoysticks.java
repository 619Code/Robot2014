package org.carobotics.hardware;

/**
 * @author Gabriel Smith
 */

public class SixenseControllerJoysticks extends Joystick {
    protected edu.wpi.first.wpilibj.Joystick joystick2;
    public SixenseControllerJoysticks(int i) {
        super(i);
        joystick2 = new edu.wpi.first.wpilibj.Joystick(i+1);
    }

    public double[] getPos() {
        double[] arr = new double[3];
        arr[0] = joystick.getZ();
        arr[1] = joystick.getThrottle();
        arr[2] = joystick.getTwist();
        return arr;
    }

    public double[] getRot() {
        double[] arr = new double[3];
        arr[0] = joystick2.getX();
        arr[1] = joystick2.getY();
        arr[2] = joystick2.getZ();
        return arr;
    }

    public double getTrigger() {
        return joystick2.getTwist();
    }
}
