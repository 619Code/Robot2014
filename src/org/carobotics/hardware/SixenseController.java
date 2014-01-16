package org.carobotics.hardware;

import org.carobotics.logic.InputManager;

/**
 * @author Gabriel Smith
 */
public class SixenseController extends Joystick {
    public static class Button {
        public static final int BUTTON1 = 1;
        public static final int BUTTON2 = 2;
        public static final int BUTTON3 = 3;
        public static final int BUTTON4 = 4;
        public static final int START = 5;
        public static final int BUMPER = 6;
        public static final int JOYSTICK = 7;
    }

    public static class Axis {
        public static final int AXIS_X = 0;
        public static final int AXIS_Y = 1;
        public static final int POS_X = 3;
        public static final int POS_Y = 4;
        public static final int POS_Z = 5;
        public static final int HEADING = 6;
        public static final int ATTITUDE = 7;
        public static final int BANK = 8;
        public static final int TRIGGER = 9;
        public static final int MAX_AXIS_VALUE = 10;
    }
    private boolean reversedAxis[];
    private double deadband = 0.05;
    private InputManager input;
    private String side;

    public SixenseController(int id, InputManager input) {
        this.input = input;
        side = id == 0 ? "L" : "R";
        reversedAxis = new boolean[Axis.MAX_AXIS_VALUE];
    }

    public SixenseController(int id, InputManager input, double deadband) {
        this(id, input);
        this.deadband = Math.abs(deadband);
    }

    public boolean getButton(int button) {
        switch(button) {
            case Button.BUTTON1:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 5)) != 0;
            case Button.BUTTON2:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 6)) != 0;
            case Button.BUTTON3:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 3)) != 0;
            case Button.BUTTON4:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 4)) != 0;
            case Button.START:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & 0x01) != 0;
            case Button.BUMPER:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 7)) != 0;
            case Button.JOYSTICK:
                return (((Double) input.getValues(side + "Buttons").firstElement()).intValue() & (0x01 << 8)) != 0;
            default:
                System.out.println("Invalid button ID: " + button);
                return false;
        }
    }

    public double getAxis(int axis) {
        return getAxis(axis, false);
    }

    public double getAxis(int axis, boolean overrideReverse) {
        if(!overrideReverse && reversedAxis[axis]) {
            if(axis == Joystick.Axis.AXIS_THROTTLE) {
                return (-1.0 * getAxis(axis, true)) + 1.0; // The throttle axis is 0 to 1
            } else {
                return -1.0 * getAxis(axis, true);
            }
        }

        double val;

        switch(axis) {
            case Axis.AXIS_X:
                val = ((Double) input.getValues(side + "JoystickY").firstElement()).doubleValue();
                break;
            case Axis.AXIS_Y:
                val = ((Double) input.getValues(side + "JoystickY").firstElement()).doubleValue();
                break;
            case Axis.POS_X:
                val = ((Double) input.getValues(side + "PosX").firstElement()).doubleValue();
                break;
            case Axis.POS_Y:
                val = ((Double) input.getValues(side + "PosY").firstElement()).doubleValue();
                break;
            case Axis.POS_Z:
                val = ((Double) input.getValues(side + "PosZ").firstElement()).doubleValue();
                break;
            case Axis.HEADING:
                val = ((Double) input.getValues(side + "Heading").firstElement()).doubleValue();
                break;
            case Axis.ATTITUDE:
                val = ((Double) input.getValues(side + "Attitude").firstElement()).doubleValue();
                break;
            case Axis.BANK:
                val = ((Double) input.getValues(side + "Bank").firstElement()).doubleValue();
                break;
            case Axis.TRIGGER:
                val = ((Double) input.getValues(side + "Trigger").firstElement()).doubleValue();
                break;
            default:
                return 0;
        }

        if(deadband < Math.abs(val)) {
            val = 0;
        }

        return val;
    }

    public void setReversed(int axis, boolean isReversed) {
        reversedAxis[axis] = isReversed;
    }
}
