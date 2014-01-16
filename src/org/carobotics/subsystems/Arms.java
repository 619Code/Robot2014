package org.carobotics.subsystems;

import org.carobotics.hardware.Potentiometer;
import org.carobotics.hardware.TalonPot;

/*
 * @author carobotics
 */
public class Arms {
    private TalonPot shoulders, hips, leftWrist, rightWrist;

    public Arms(int shoulderChannel, Potentiometer shoulderPot, int hipChannel, Potentiometer hipPot, int leftWristChannel, Potentiometer leftWristPot, int rightWristChannel, Potentiometer rightWristPot) {
        shoulders = new TalonPot(shoulderChannel, shoulderPot);
        hips = new TalonPot(hipChannel, hipPot);
        leftWrist = new TalonPot(leftWristChannel, leftWristPot);
        rightWrist = new TalonPot(rightWristChannel, rightWristPot);
    }
    
    public void setShouldersPercent(double val) {
        shoulders.set(val);
    }

    public void setHipsPercent(double val) {
        hips.set(val);
    }

    public void setWristsPercent(double val) {
        leftWrist.set(val);
        rightWrist.set(val);
    }
    
    public void setLeftWristPercent(double val) {
        leftWrist.set(val);
    }
    
    public void setRightWristPercent(double val) {
        rightWrist.set(val);
    }

    public TalonPot getHips() {
        return hips;
    }

    public TalonPot getLeftWrist() {
        return leftWrist;
    }

    public TalonPot getRightWrist() {
        return rightWrist;
    }

    public TalonPot getShoulders() {
        return shoulders;
    }
    
    
}
