package org.carobotics.subsystems.r2012;

import edu.wpi.first.wpilibj.DigitalInput;
import org.carobotics.hardware.AnalogDigitalInput;
import org.carobotics.hardware.CANJaguar;

/**
 *
 * @author CaRobotics
 */
public class Lifter {

    private CANJaguar CANLifter;
    private AnalogDigitalInput bottomLimit;
    private AnalogDigitalInput bottomWarning;
    private AnalogDigitalInput upperLimit;
    private AnalogDigitalInput upperWarning;
    private AnalogDigitalInput levelOneDetector;
    private AnalogDigitalInput upperSmallPieceLimit;

    public Lifter(int canLifter, int moduleNumber, int bottomLimit, int bottomWarning, int upperLimit, int upperWarning, int leveOneDetector) {
        this.CANLifter = new CANJaguar(canLifter);
        this.bottomLimit = new AnalogDigitalInput(moduleNumber, bottomLimit);
        this.bottomWarning = new AnalogDigitalInput(moduleNumber, bottomWarning);
        this.upperLimit = new AnalogDigitalInput(moduleNumber, upperLimit);
        this.upperWarning = new AnalogDigitalInput(moduleNumber, upperWarning);
        this.levelOneDetector = null;
        this.upperSmallPieceLimit = new AnalogDigitalInput(moduleNumber, 7);
    }
    
    public boolean getUpperSmallPieceDetector(){
        return upperSmallPieceLimit.get();
    }

    public boolean getBottomLimit() {
        return bottomLimit.get();
    }

    public boolean getBottomWarning() {
        return bottomWarning.get();
    }

    public boolean getOnePointLimit() {
        //return levelOneDetector.get();
        return false;
    }

    public CANJaguar getLifterMotor() {
        return CANLifter;
    }

    public boolean getUpperLimit() {
        return upperLimit.get();
    }

    public boolean getUpperWarning() {
        return upperWarning.get();
    }

    public AnalogDigitalInput getBottomLimitADI() {
        return bottomLimit;
    }

    public AnalogDigitalInput getBottomWarningADI() {
        return bottomWarning;
    }

    public AnalogDigitalInput getOnePointLimitADI() {
        return levelOneDetector;
    }

    public AnalogDigitalInput getUpperLimitADI() {
        return upperLimit;
    }

    public AnalogDigitalInput getUpperWarningADI() {
        return upperWarning;
    }
}
