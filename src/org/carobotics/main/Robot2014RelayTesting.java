/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.carobotics.logic.ThreadManager;
import org.carobotics.hardware.Relay;

/**
 *
 * @author Student
 */
public class Robot2014RelayTesting extends IterativeRobot{
    // Robot Systems (stuff from org.carobotics.subsystems)
    Relay relay;
    
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        System.out.println("\n");
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2014 Relay Testing                           //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        
        relay = new Relay(7);
        
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
    
    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
        System.out.println("is now on");
        relay.setOn();
        
        
        try{
            Thread.sleep(5000);
        }catch(Exception e){}
        
        
        System.out.println("no longer on");
        
        relay.setForward();
        System.out.println("being set forward");
        
        try{
            Thread.sleep(5000);
        }catch(Exception e){}
     
        System.out.println("no longer forward");
        
    }
    
    public void teleopPeriodic(){
    }

    public void teleopContinuous() {
    }

    public void disabledInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }
    
    public void disabledPeriodic(){
    }
    
    public void disabledContinuous(){
    }
}
