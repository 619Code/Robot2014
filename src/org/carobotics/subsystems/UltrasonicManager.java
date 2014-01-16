package org.carobotics.subsystems;

import org.carobotics.hardware.Ultrasonic;
import org.carobotics.hardware.UltrasonicRing;

/**
 *
 * @author CaRobotics
 */
public class UltrasonicManager {

    private UltrasonicRing ultras;
    private Ultrasonic leftSensor;
    private Ultrasonic rightSensor;

    public UltrasonicManager(Ultrasonic left, Ultrasonic right) {
        ultras = new UltrasonicRing();
        leftSensor = left;
        rightSensor = right;
        ultras.addSensor(left);
        ultras.addSensor(right);
    }

    public UltrasonicManager(int leftDigitalMod, int leftDigitalChannel, int leftAnalogMod, int leftAnalogChannel,
            int rightDigitalMod, int rightDigitalChannel, int rightAnalogMod, int rightAnalogChannel) {
        leftSensor = new Ultrasonic(leftDigitalMod, leftDigitalChannel, leftAnalogMod, leftAnalogChannel);
        rightSensor = new Ultrasonic(rightDigitalMod, rightDigitalChannel, rightAnalogMod, rightAnalogChannel);
        ultras.addSensor(leftSensor);
        ultras.addSensor(rightSensor);
    }

    public Ultrasonic getLeftSensor() {
        return leftSensor;
    }

    public Ultrasonic getRightSensor() {
        return rightSensor;
    }

//    public void addSensor(Ultrasonic sensor) {
//        ultras.addSensor(sensor);
//    }
//    public Ultrasonic addSensor(int pingChannel, int echoChannel) {
//        return ultras.addSensor(pingChannel, echoChannel);
//    }
    public void start() {
        ultras.start();
    }

    public void stop() {
        ultras.stop();
    }
}
