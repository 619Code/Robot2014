package org.carobotics.hardware;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * @author CaRobotics
 */
public class TalonPot extends Talon implements PIDOutput {
    private Potentiometer pot;
    private PIDController pid;

    public TalonPot(int channel, Potentiometer pot) {
        super(channel);
        this.pot = pot;
    }

    public TalonPot(int channel, int slot, Potentiometer pot) {
        super(slot, channel);
        this.pot = pot;
    }

    public Potentiometer getPotentiometer(){
        return pot;
    }
    
    public double getAngle(){
        return pot.getAngle();
    }

    public void pidWrite(double d) {
        talon.pidWrite(d);
    }
}
