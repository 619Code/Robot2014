package org.carobotics.logic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.TankDriveBase;

/**
 * @author CaRobotics
 */
public class PIDTuningThread extends RobotThread {
    protected DriverStation driverStation;
    protected ArmMappingThread armThread;

    public PIDTuningThread(TankDriveBase tankDriveBase, ArmMappingThread armThread,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.driverStation = driverStation;
        this.armThread = armThread;
    }

    protected void cycle() {
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.TRIGGER)) {
            // STOP
            armThread.setAllowPidControl(false);
            armThread.getArmPID().reset();
            armThread.getLegPID().reset();
            armThread.getWristLeftPID().reset();
            armThread.getWristRightPID().reset();
            armThread.getArms().setHipsPercent(0.0);
            armThread.getArms().setShouldersPercent(0.0);
            armThread.getArms().setWristsPercent(0.0);
            super.stopRunning();
            return;
        }

        PIDController loopToTune = null;
        PIDController loopToTune2 = null;

        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
            // Tune arms
            loopToTune = armThread.getArmPID();
        } else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON7)) {
            // Tune legs
            loopToTune = armThread.getLegPID();
        } else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON11)) {
            // Tune wrists
            loopToTune = armThread.getWristLeftPID();
            loopToTune2 = armThread.getWristRightPID();
        }

        if(loopToTune != null) {
            tune(loopToTune);
        }
        if(loopToTune2 != null) {
            tune(loopToTune2);
        }
    }

    private void tune(PIDController pid) {
        double angleToMove = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_THROTTLE);
        angleToMove *= 2.0;
        angleToMove -= 1.0;
        angleToMove *= 30.0; // between -30 and 30 degrees

        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)) { // test move
            pid.setSetpoint(pid.getSetpoint() + angleToMove);
        }

        double constantChangeSign = 0.0;
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON8)) {
            constantChangeSign = -1.0;
        }
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON9)) {
            constantChangeSign = 1.0;
        }

        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON4)) { // P
            double delta = constantChangeSign * 0.0001;
            pid.setPID(pid.getP() + delta, pid.getI(), pid.getD());
        } else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON3)) { // I
            double delta = constantChangeSign * 0.0001;
            pid.setPID(pid.getP(), pid.getI() + delta, pid.getD());
        } else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON5)) { // D
            double delta = constantChangeSign * 0.0001;
            pid.setPID(pid.getP(), pid.getI(), pid.getD() + delta);
        }

        System.out.println("PID] P = " + pid.getP() + " I = " + pid.getI() + " D = " + pid.getD() + " Set = " + pid.getSetpoint() + " Error = " + pid.getError() + " OnTarget = " + pid.onTarget() + " Output = " + pid.get());
        SmartDashboard.putNumber("Curr PID - P", pid.getP());
        SmartDashboard.putNumber("Curr PID - I", pid.getI());
        SmartDashboard.putNumber("Curr PID - D", pid.getD());
        SmartDashboard.putNumber("Curr PID - Setpt", pid.getSetpoint());
        SmartDashboard.putNumber("Curr PID - Error", pid.getError());
    }
}
