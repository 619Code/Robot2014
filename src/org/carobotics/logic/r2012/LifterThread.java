package org.carobotics.logic.r2012;

import org.carobotics.hardware.Joystick;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.r2012.FourStickDriverStation2012;
import org.carobotics.subsystems.r2012.Lifter;

/**
 *
 * @author CaRobotics
 */
public class LifterThread extends RobotThread {
    private int mode = LifterMode.MANUAL;
    private long lastTimeBelowLevelOne = 0;
    private Lifter lifter;
    private FourStickDriverStation2012 driverStation;

    public static class LifterMode {
        public static final int MOVE_TO_BOTTOM = 0;
        public static final int MOVE_TO_TOP = 1;
        public static final int MOVE_TO_LEVEL_ONE = 2;
        public static final int MANUAL = 3;
        public static final int DO_NOTHING = 4;
    }

    public LifterThread(Lifter lifter, FourStickDriverStation2012 driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.lifter = lifter;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        switch(mode) {
            case LifterMode.MOVE_TO_BOTTOM:
                moveToBottom();
                return;
            case LifterMode.MOVE_TO_TOP:
                moveToTop();
                return;
            case LifterMode.MOVE_TO_LEVEL_ONE:
                moveToLevelOne();
                return;
            case LifterMode.MANUAL:
                manual();
                return;
            case LifterMode.DO_NOTHING:
                return; // Do nothing
        }
    }

    private void moveToBottom() {
        lifter.getLifterMotor().set(applyLimitBounds(-0.7));
    }

    private void moveToTop() {
        lifter.getLifterMotor().set(applyLimitBounds(1.0));
    }

    private void moveToLevelOne() {
        System.out.println("LEVEL ONE NOT IMPLEMENTED!");
//        if (!lifter.getOnePointLimit()) {
//            lifter.getLifterMotor().set(applyLimitBounds(1.0));
//            lastTimeBelowLevelOne = System.currentTimeMillis();
//        } else {
//            if (System.currentTimeMillis() - lastTimeBelowLevelOne < 300) {
//                mode = LifterMode.DO_NOTHING; // We should be done
//            } else {
//                lifter.getLifterMotor().set(applyLimitBounds(-1.0)); // We could be above, so go down
//            }
//        }
    }

    private void manual() {
        lifter.getLifterMotor().set(applyLimitBounds(driverStation.getLifterJoystick().getAxis(Joystick.Axis.AXIS_Y)));
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        if(mode > 4 || mode < 0) {
            this.mode = LifterMode.DO_NOTHING;
        } else {
            this.mode = mode;
        }
    }

    private double applyLimitBounds(double value) {
        // TODO: refactor these constants out as variables
        if(value > 1.0) {
            value = 1.0;
        }
        if(value < -1.0) {
            value = -1.0;
        }

        // Absolute limits:
        if(lifter.getBottomLimit() && lifter.getUpperLimit()) {
            if(value < 0) {
                value = 0; // Can't go down from the bottom;
            }
        }

        if((lifter.getUpperLimit() && lifter.getUpperWarning()) && lifter.getUpperSmallPieceDetector()) {
            if(value > 0) {
                value = 0; // Can't go up from the top
            }
        }

        // Slow speed zones
        if(lifter.getBottomWarning()) {
            if(value < -0.3) {
                value = -0.3;
            }
        }

        if(lifter.getUpperWarning()) {
            if(value > 0.7) {
                value = 0.7;
            }
        }

        printLimits();

        return value;
    }

    public Lifter getLifter() {
        return lifter;
    }

    public void printLimits() {
        System.out.println("Lift Limits: UL - " + lifter.getUpperLimit() + " UW - " + lifter.getUpperWarning() + " 1P - " + lifter.getOnePointLimit() + " LW - " + lifter.getBottomWarning() + " LL - " + lifter.getBottomLimit());
    }
}
