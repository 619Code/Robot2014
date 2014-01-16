package org.carobotics.subsystems.r2012;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.KinectStick;

/**
 *
 * @author CaRobotics
 */
public class KinectJoystickInterface {

    KinectStick leftArm;
    KinectStick rightArm;

    public static class Button {

        public static final int liftButton = 0;
        public static final int collectButton = 1;
    }
    /*
    RawButton 
    1) Head to the Right (the head buttons do not work well with arms straight up)
    2) Head to the Left (the head buttons do not work well with arms straight up)
    3) Right leg out to the right
    4) Left Leg out to the left
    5) Right Leg Forward
    6) Right Leg Back
    7) Left Leg Forward
    8) Left Leg Back
     */

    public static class Axis {

        public static final int AxisLeft = 0;
        public static final int AxisRight = 1;
    }

    public double getAxis(int axis) {
        switch (axis) {
            case Axis.AxisLeft:
                return leftArm.getY();
            case Axis.AxisRight:
                return rightArm.getY();
            default:
                return 0;
        }
    }

    public boolean getButton(int button) {
        switch (button) {
            case Button.liftButton:
                return leftArm.getRawButton(1);
            case Button.collectButton:
                return leftArm.getRawButton(2);
            default:
                return false;
        }
    }
}