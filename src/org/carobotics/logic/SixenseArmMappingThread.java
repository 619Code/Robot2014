package org.carobotics.logic;

import com.sun.squawk.util.MathUtils;
import org.carobotics.hardware.SixenseController;
import org.carobotics.subsystems.SixenseArm;
import org.carobotics.subsystems.SixenseDriverStation;

/**
 * @author Gabriel Smith
 */
public class SixenseArmMappingThread extends RobotThread {
    private SixenseArm arm;
    private SixenseDriverStation driverStation;
    private boolean inverseKinematics = false;

    public SixenseArmMappingThread(SixenseArm arm, SixenseDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.arm = arm;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        if(inverseKinematics) {
            matchPosition(driverStation.getRightJoystick().getAxis(SixenseController.Axis.POS_X), driverStation.getRightJoystick().getAxis(SixenseController.Axis.POS_X), 5, 7, 14, 0, 0);
        } else {
            moveUpper(driverStation.getLeftJoystick().getAxis(SixenseController.Axis.AXIS_Y));
            moveLower(driverStation.getRightJoystick().getAxis(SixenseController.Axis.AXIS_Y));
        }

        if(driverStation.getRightJoystick().getAxis(SixenseController.Axis.TRIGGER) >= .5) {
            System.out.println("Claw triggered");
            arm.getClawSolenoid().set(true);
        } else {
            arm.getClawSolenoid().set(false);
        }
    }
    
    public void matchPosition(double y, double z, double upperLength, double lowerLength, double height, double upperOffset, double lowerOffset) {
        double angleElbow = MathUtils.acos((upperLength*upperLength + lowerLength*lowerLength - (height - y)*(height - y) - z*z)/(2*upperLength*lowerLength));
        double angleShoulder = 90 - MathUtils.asin(z/(height - y)) - MathUtils.acos((upperLength*upperLength - lowerLength*lowerLength + (height - y)*(height - y) + z*z)/(2*upperLength*((height - y)*(height - y) + z*z)));
        arm.getTopMotor().setPosition(angleShoulder);
        //TODO: set lower angle
    }

    public void moveUpper(double input) {
        // Check arm limits
        if(!arm.getArmUpper().get()) {
            if(input < 0) {
                input = 0;
                System.out.println("Arm at upper limit.");
            }
        }

        if(!arm.getArmLower().get()) {
            if(input > 0) {
                input = 0;
                System.out.println("Arm at lower limit.");
            }
        }

        if(input > 0.5) {
            input = 0.5;
        }
        if(input < -0.5) {
            input = -0.5;
        }

        arm.getTopMotor().set(input);
    }

    public void moveLower(double input) {
        // Check arm limits
        arm.getLowerMotor().set(input);
    }
}
