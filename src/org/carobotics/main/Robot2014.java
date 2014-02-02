/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.carobotics.logic.ActionsMappingThread;
import org.carobotics.hardware.Solenoid;
import org.carobotics.logic.TankDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.TankDriveBase;

/**
 *
 * @author Student
 */
public class Robot2014 extends IterativeRobot{
    
    // Robot Systems (stuff from org.carobotics.subsystems)
    TankDriveBase driveBase;
    FourStickDriverStation driverStation;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    TankDriveMappingThread driveThread;
    ActionsMappingThread actionsThread;
    // Actions
    TimeMovementAction forwardAction;
    Solenoid shooter1, shooter2;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("\n");// shows code is working
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Cavalier Robotics                     TEAM 619   //");
        System.out.println("// 2014 Main Code                                   //");
        System.out.println("//////////////////////////////////////////////////////\n");

        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        driveBase = new TankDriveBase(1, 2);//2L,3R

        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        
        //when we have more information put in another number
        Solenoid shooter1 = new Solenoid(1);  //pneumatic for shooter
        Solenoid shooter2 = new Solenoid(0);  //pneumatic for shooter
        
        
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        forwardAction = new TimeMovementAction(driveBase, 1, 2000, 2000, 1, 1, threadManager);
        forwardAction.begin(); //starts
        shooter1.set(true);  //starts pneumatics
        shooter2.set(true);  //starts pneumatic
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
         
        // DRIVE
        driveThread = new TankDriveMappingThread(driveBase, driverStation, 10, threadManager);
        driveThread.start();

    }

    public void teleopPeriodic() {
    }

    public void teleopContinuous() {
    }

    public void disabledInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }

    public void disabledPeriodic() {
    }

    public void disabledContinuous() {
    }
}
