/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic;

import edu.wpi.first.wpilibj.image.ColorImage;
import org.carobotics.networking.Networking;
import org.carobotics.subsystems.Vision;

/**
 *
 * @author Student
 */
public class VisionThread extends RobotThread {
    
    private Vision vision;
    private Networking networking;

    public VisionThread(Vision vision, Networking networking, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.vision = vision;
        this.networking = networking;
    }

    protected void cycle() {
        networking.queue(getBytesFromImage(vision.getImage()));
    }
    
    public static byte[] getBytesFromImage(ColorImage image) {
        try {
            int size = image.getHeight() * image.getWidth() * 3;
            byte[] data = new byte[size];
            image.image.getBytes(0, data, 0, size);
            return data;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
