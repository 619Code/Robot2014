package org.carobotics.logic.r2012;

import edu.wpi.first.wpilibj.Kinect;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.r2012.KinectJoystickInterface;
import org.carobotics.subsystems.TankDriveBase;

/**
 *
 * @author carobotics
 */
public class KinectMappingThread extends RobotThread {

    Kinect kinect;
    KinectJoystickInterface joysticks;
    TankDriveBase drive;

    public KinectMappingThread(KinectJoystickInterface joysticks, TankDriveBase drive, int period, ThreadManager threadManager) {
        super(period, threadManager);
        kinect = Kinect.getInstance();
        this.joysticks = joysticks;
        this.drive = drive;
    }

    protected void cycle() {
        drive.drive(joysticks.getAxis(KinectJoystickInterface.Axis.AxisLeft), 
                joysticks.getAxis(KinectJoystickInterface.Axis.AxisRight));
    }
}
