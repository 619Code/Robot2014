
package org.carobotics.hardware;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author CaRobotics
 */
public class Potentiometer implements PIDSource{
    
    private AnalogInput pot;
    
    private double zeroVoltAngle, degreesPerVolt;
    
    public Potentiometer(int slot, int channel, double calibVoltage1, double calibAngle1, double calibVoltage2, double calibAngle2){
        pot = new AnalogInput(slot, channel);
        calibrate(calibVoltage1, calibAngle1, calibVoltage2, calibAngle2);
    }
    
    public Potentiometer(int channel, double calibVoltage1, double calibAngle1, double calibVoltage2, double calibAngle2){
        pot = new AnalogInput(channel);
        calibrate(calibVoltage1, calibAngle1, calibVoltage2, calibAngle2);
    }
    
    public void calibrate(double calibVoltage1, double calibAngle1, double calibVoltage2, double calibAngle2){
        degreesPerVolt = (calibAngle2 - calibAngle1)/ (calibVoltage2 - calibVoltage1);
        zeroVoltAngle = calibAngle1 - (degreesPerVolt * calibVoltage1);
    }
    
    public double getRaw(){
        return pot.getVoltage();
    }
    
    public double getAngle(){
        return zeroVoltAngle + (pot.getVoltage() * degreesPerVolt);
    }

    public double pidGet() {
        return getAngle();
    }
}
