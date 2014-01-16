package org.carobotics.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Solenoid;

/**
 * @author Gabriel Smith
 */
public class SixenseArm {
    CANJaguar topMotor;
    Victor lowerMotor;
    DigitalInput armUpper, armLower;
    Solenoid claw;

    public SixenseArm(int topJagId, int lowerVictorId, int topLimInpt, int botLimInpt, int solenoidId) {
        topMotor = new CANJaguar(topJagId);
        lowerMotor = new Victor(lowerVictorId);
        armUpper = new DigitalInput(topLimInpt);
        armLower = new DigitalInput(botLimInpt);

        claw = new Solenoid(solenoidId);
    }

    public CANJaguar getTopMotor() {
        return topMotor;
    }

    public Victor getLowerMotor() {
        return lowerMotor;
    }

    public DigitalInput getArmLower() {
        return armLower;
    }

    public DigitalInput getArmUpper() {
        return armUpper;
    }

    public Solenoid getClawSolenoid() {
        return claw;
    }
}
