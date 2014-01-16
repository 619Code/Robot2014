/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.carobotics.hardware.Talon;
import org.carobotics.logic.AutonomousBumpThread;
import org.carobotics.logic.DumperMappingThread;
import org.carobotics.logic.TalonFanThread;
import org.carobotics.logic.TalonTankDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.AutonomousSelector;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013TalonDriveOnly extends IterativeRobot {
     // Robot Systems (stuff from org.carobotics.subsystems)
    Talon leftTalon, leftTalon2;
    Talon rightTalon, rightTalon2;
    FourStickDriverStation driverStation;
    //FrisbeeDumper dumper;
    AutonomousSelector autonomousSelector;
    TalonFanThread fanThread;
    
    Talon fanTalon;
    
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    TalonTankDriveMappingThread driveThread;
    DumperMappingThread dumperThread;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        System.out.println("\n");
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2013 Talon                                   //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        
        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        leftTalon = new Talon(2);
        rightTalon = new Talon(4);
        leftTalon2 = new Talon(3);
        rightTalon2 = new Talon(5);
        leftTalon.setReversed(true);
        leftTalon2.setReversed(true);
        
        
        fanTalon = new Talon(1);
        
        //dumper = new FrisbeeDumper(5, 6, 7);
        
        

        //autonomousSelector = new AutonomousSelector();
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        /*
        if(autonomousSelector.getRunMode() == AutonomousSelector.RunMode.DO_SOMETHING){
            ActionSequence seq = new ActionSequence();
            
            seq.add(new TalonTimeDriveAction(leftTalon, rightTalon, 100, 10, 7000, -0.8, threadManager, seq.getLast()));
            seq.add(new DumperAction(dumper, 100, 10, threadManager, seq.getLast()));
            
            seq.start();
        }
         *
         */
        
        
        
        AutonomousBumpThread autoBump = new AutonomousBumpThread(leftTalon, rightTalon, leftTalon2, rightTalon2, driverStation, 20, threadManager);
        if(autonomousSelector.getRunMode() == AutonomousSelector.RunMode.DO_SOMETHING) autoBump.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
    
    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!

        driveThread = new TalonTankDriveMappingThread(leftTalon, rightTalon, leftTalon2, rightTalon2, driverStation, 15, threadManager);
        driveThread.start(); 
        
        fanThread = new TalonFanThread(driverStation, fanTalon, 20, threadManager);
        fanThread.start();
        
        
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
