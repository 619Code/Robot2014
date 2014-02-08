/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.main;

import org.carobotics.subsystems.FourStickDriverStation;

/**
 *
 *
 * @author Student
 */
import org.carobotics.logic.ThreadManager;
public class pneumaticsTesting {
    
    ThreadManager threadManager;
    
    public void robotInit() {

        System.out.println("\n"); //printout to prove workability
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2014 pneumatics testing                      //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG        
        
        
       
//        Potentiometer shoulderPot = new Potentiometer(1, 0.0, 0.0, 5.0, 360.0);
//        arms = new Arms(1, shoulderPot, 2, shoulderPot, 3, shoulderPot, 4, shoulderPot);
//        feet = new GripperPair(2, 3, 6, 7);
//        hands = new GripperPair(4, 5, 8, 9);
                
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
    }
    
    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!

    }
    
    public void teleopPeriodic() {
    }
        

    public void disabledInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }    
}
