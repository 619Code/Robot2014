package org.carobotics.hardware;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;
import java.util.Vector;

/**
 * @author carobotics
 */
public class UltrasonicRing {
    
    private Vector sensors;
    private boolean isRunning = false;
    private Thread thread;
    
    public UltrasonicRing(Vector ring) {
        if (ring == null) {
            ring = new Vector();
        }
        sensors = new Vector();
        for (int i = 0; i < ring.size(); i++) {
            Object o = ring.elementAt(i);
            if (o != null) {
                try {
                    Ultrasonic sensor = (Ultrasonic) o;
                    sensors.addElement(sensor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public UltrasonicRing() {
        sensors = new Vector();
    }
    
    public void addSensor(Ultrasonic sensor) {
        if (sensors.contains(sensor)) {
            return;
        }
        sensors.addElement(sensor);
    }
    
    public Ultrasonic addSensor(DigitalOutput output, AnalogChannel input) {
        Ultrasonic sensor = new Ultrasonic(output, input);
        sensors.addElement(sensor);
        return sensor;
    }
    
    public Ultrasonic addSensor(int digitalMod, int digitalChannel, int analogMod, int analogChannel) {
        Ultrasonic sensor = new Ultrasonic(digitalMod, digitalChannel, analogMod, analogChannel);
        sensors.addElement(sensor);
        return sensor;
    }
    
    public void removeSensor(Ultrasonic sensor) {
        sensors.removeElement(sensor);
    }
    
    public void stop() {
        isRunning = false;
    }
    
    public void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(new Runnable() {
                
                public void run() {
                    int location = 0;
                    while (isRunning) {
                        if (location < sensors.size()) {
                            try {
                                Ultrasonic sensor = (Ultrasonic) sensors.elementAt(location);
                                
                                sensor.update();
                                
                                location++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            location = 0;
                            
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });
            thread.start();
        }
    }
}
