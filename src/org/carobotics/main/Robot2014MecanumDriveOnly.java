/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.carobotics.logic.MecanumDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.MecanumDriveBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2014MecanumDriveOnly extends IterativeRobot{
    
    // Robot Systems (stuff from org.carobotics.subsystems)
    FourStickDriverStation driverStation;
    MecanumDriveBase driveBase;
    
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    
    // Logic Threads (stuff from org.carobotics.logic)
    MecanumDriveMappingThread driveThread;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        System.out.println("\n");
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2014 Jaguar Mecanum Drive Only               //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        
        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        driveBase = new MecanumDriveBase(1, 2, 3, 4);
            
        //autonomousSelector = new AutonomousSelector();
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

        driveThread = new MecanumDriveMappingThread(driveBase, driverStation, 15, threadManager);
        driveThread.start();    
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
