package org.carobotics.subsystems;

import org.carobotics.hardware.DualCANJaguars;
import org.carobotics.hardware.Servo;

/**
 *
 * @author thomas
 */
public class DualMotorTankDriveBase extends TankDriveBase {

    public DualMotorTankDriveBase(int leftId, int rightId, int slaveLeftId, int slaveRightId) {
        super.leftMotor = new DualCANJaguars(leftId, slaveLeftId);
        super.rightMotor = new DualCANJaguars(rightId, slaveRightId);

        super.leftShifter = new Servo(1, 1);
        super.rightShifter = new Servo(1, 2);
    }
}