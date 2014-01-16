package org.carobotics.logic.r2012;

import org.carobotics.hardware.Joystick;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.r2012.BallManipulator;
import org.carobotics.subsystems.r2012.FourStickDriverStation2012;

/**
 *
 * @author CaRobotics
 */
public class BallManipulatorMappingThread extends RobotThread {

    private BallManipulator ballManipulator;
    private FourStickDriverStation2012 driverStation;
    private long lastRailChange = 0;

    public BallManipulatorMappingThread(BallManipulator ballManipulator, FourStickDriverStation2012 driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.ballManipulator = ballManipulator;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        if (System.currentTimeMillis() - lastRailChange > 2000) {
            if (driverStation.getBallManipulatorJoystick().getButton(Joystick.Button.BUTTON3)) {
                ballManipulator.raiseRails();
            }
            if (driverStation.getBallManipulatorJoystick().getButton(Joystick.Button.BUTTON2)) {
                ballManipulator.lowerRails();
            }
        }

        ballManipulator.getMotor().setPercent(driverStation.getBallManipulatorJoystick().getAxis(Joystick.Axis.AXIS_Y));
    }
}
