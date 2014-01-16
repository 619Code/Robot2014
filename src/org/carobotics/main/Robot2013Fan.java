/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.PIDController;
//import java.util.Vector;
import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Joystick;
import org.carobotics.logic.DumperMappingThread;
import org.carobotics.logic.FanThread;
import org.carobotics.logic.TankDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.DumperAction;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.subsystems.AutonomousSelector;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.FrisbeeDumper;
import org.carobotics.subsystems.TankDriveBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013Fan extends IterativeRobot {
    // Robot Systems (stuff from org.carobotics.subsystems)
    TankDriveBase driveBase;
    FourStickDriverStation driverStation;
    CANJaguar fan;
    AutonomousSelector autonomousSelector;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    TankDriveMappingThread driveThread;
    FanThread fanThread;
    // Actions

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("\n");
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Cavalier Robotics                     TEAM 619   //");
        System.out.println("// 2013 Main Code                                   //");
        System.out.println("//////////////////////////////////////////////////////\n");

        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        driveBase = new TankDriveBase(6, 7);//2L,3R
        driveBase.getLeftJaguar().setReversed(true);

        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        driverStation.getRightJoystick().setReversed(Joystick.Axis.AXIS_Y, true);
        
        fan = new CANJaguar(3);
        
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }

    /*
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
