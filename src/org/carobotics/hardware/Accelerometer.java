package org.carobotics.hardware;

import edu.wpi.first.wpilibj.ADXL345_I2C;

/**
 * @author CaRobotics
 */
public class Accelerometer {
    public static class Axis{
        public static ADXL345_I2C.Axes X = ADXL345_I2C.Axes.kX;
        public static ADXL345_I2C.Axes Y = ADXL345_I2C.Axes.kY;
        public static ADXL345_I2C.Axes Z = ADXL345_I2C.Axes.kZ;
    }
    
    private edu.wpi.first.wpilibj.ADXL345_I2C accelerometer;
    
    public Accelerometer(int slot){
        accelerometer = new ADXL345_I2C(slot, ADXL345_I2C.DataFormat_Range.k2G);
    }
    
    public double getAxis(ADXL345_I2C.Axes axis){
        return accelerometer.getAcceleration(axis);
    }
}
