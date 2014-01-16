/*
 * Failure v1
 * by NamTaey and Baka
 * Program will "drive" a robot for a designated time and if different directions.
 */
package org.carobotics.main;

import org.carobotics.subsystems.TalonDriveBase;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.carobotics.hardware.Talon;
//import org.carobotics.logic.DumperMappingThread;
import org.carobotics.logic.TalonTankDriveMappingThread;
import org.carobotics.logic.ThreadManager;
//import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.logic.actions.*;

public class Failurev1 extends IterativeRobot {
//    Talon leftTalon, leftTalon2;
//    Talon rightTalon, rightTalon2;
    Talon leftTalon, rightTalon;
    TalonDriveBase driveBase;
    DriverStation driverStation;
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    TalonTankDriveMappingThread driveThread;
    TimeMovementAction forwardAction;
    TimeMovementAction backwardAction;
    TimeMovementAction turnLeftAction;
    TimeMovementAction turnRightAction;

    public void robotInit() {

        System.out.println("\n");
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Team Awesome                          TEAM 619   //");
        System.out.println("// 2013 Failure v1                                  //");
        System.out.println("//////////////////////////////////////////////////////\n");

        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG

        leftTalon = new Talon(1);
        rightTalon = new Talon(2);
        leftTalon.setReversed(true);
        
        //driveBase = new TalonDriveBase(leftTalon, rightTalon);
        
        driverStation = new DriverStation();

        forwardAction = new TimeMovementAction(driveBase, 100, 100, 1000, 0.3, 0.3, threadManager);
        //backwardAction = new TimeMovementAction(driveBase, 100, 100, 1000, -0.3, -0.3, threadManager, forwardAction);
        //turnLeftAction = new TimeMovementAction(driveBase, 100, 100, 500, -0.3, 0.3, threadManager);
        //turnRightAction = new TimeMovementAction(driveBase, 100, 100, 500, 0.3, -0.3, threadManager, turnLeftAction);

    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        turnLeftAction.start();
//        leftTalon.set(1.0);
//        rightTalon.set(1.0);
    }

    public void autonomousPeriodic() {
        
    }

    public void autonomousContinuous() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!

        //driveThread = new TankDriveMappingThread(driveBase, driverStation, 15, threadManager);
        driveThread.start();
        
//        driveThread = new TalonTankDriveMappingThread(leftTalon, rightTalon, leftTalon2, rightTalon2, driverStation, 15, threadManager);
//        driveThread.start();

        // fanThread = new TalonFanThread(driverStation, fanTalon, 20, threadManager);
        //  fanThread.start();


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
