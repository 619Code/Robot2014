package org.carobotics.hardware;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * @author CaRobotics
 */
public class Ultrasonic {

    private DigitalOutput output;
    private AnalogChannel input;
    private static final double PULSE_TIME_SECONDS = 0.000020;
    private static final double INCHES_PER_VOLT = 102.396068;
    private static final double CM_PER_VOLT = 260.086013;
    private static final double MM_PER_VOLT = 2600.86013;

    //Brown dot sensor is reversed: Closer is higher, farther is lower
    //Need to add parameters to define reversed, and distance per volt
    public Ultrasonic(DigitalOutput output, AnalogChannel input) { 
        this.output = output;
        this.input = input;
    }

    public Ultrasonic(int digitalMod, int digitalChannel, int analogMod, int analogChannel) {
        output = new DigitalOutput(digitalMod, digitalChannel);
        input = new AnalogChannel(analogMod, analogChannel);
    }

    public void update() {
        output.pulse(PULSE_TIME_SECONDS);
    }

    public double getVoltage() {
        return input.getVoltage();
    }

    public double getDistanceIn() {
        return input.getVoltage() * INCHES_PER_VOLT;
    }

    public double getDistanceCM() {
        return input.getVoltage() * CM_PER_VOLT;
    }

    public double getDistanceMM() {
        return input.getVoltage() * MM_PER_VOLT;
    }
}
