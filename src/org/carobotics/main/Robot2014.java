/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.carobotics.hardware.Solenoid;
import org.carobotics.hardware.Compressor;
import org.carobotics.hardware.Talon;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.logic.LiftyThingMappingThread;
import org.carobotics.logic.ThwackerMappingThread;
import org.carobotics.logic.TalonTankDriveMappingThread;
import org.carobotics.logic.CompressorThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.logic.actions.ThwackingAction;
import org.carobotics.logic.actions.LiftyThingAction;
import org.carobotics.subsystems.Thwacker;
import org.carobotics.subsystems.LiftyThing;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.TalonDriveBase;

/**
 *
 * The robot for this year has two manipulators, the liftyThing, for picking up
 * a 2 ft diameter ball off the ground and a thwacker that hits/shoots the ball
 * 
 * @author Student
 */
public class Robot2014 extends IterativeRobot{
    
    // Robot Systems (stuff from org.carobotics.subsystems)
    Compressor comp;
    Talon leadScrew;
    Thwacker thwacker;
    LiftyThing liftyThing;
    TalonDriveBase driveBase;
    FourStickDriverStation driverStation;
    Solenoid shooter1, shooter2;
    DigitalInput leadTop, leadBottom, liftTop, liftBottom;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    LiftyThingMappingThread liftyThread;
    ThwackerMappingThread thwackerThread;
    TalonTankDriveMappingThread driveThread;
    CompressorThread compressorThread;
    // Actions
    TimeMovementAction forwardAction;
    ThwackingAction thwackingAction;
    LiftyThingAction liftyAction;
    
    
    
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
        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        
        //plug into digital output on the digital sidecar
        driveBase = new TalonDriveBase(1, 2);
        leadScrew = new Talon(3);
        liftyThing = new LiftyThing(4, 6, 1);
        
        //plug into digital input on the digital sidecar
        shooter1 = new Solenoid(1);
        shooter2 = new Solenoid(2); 
        leadTop = new DigitalInput(11);
        leadBottom = new DigitalInput(12);
        liftTop = new DigitalInput(13);
        liftBottom = new DigitalInput(14);
        comp = new Compressor(7, 8);
        thwacker = new Thwacker(shooter1, shooter2, comp);
        
        
        
        
    }

    public void autonomousInit() {
        
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        //if you have something not from org.carobotics.logic.actions in here, then you're doing it wrong!!
        //you'll crash the bot when it changes from autonomous to tele-op
        forwardAction = new TimeMovementAction(driveBase, 1, 1000, 1000, 1, 1, threadManager);//moves forward 1 second
        thwackingAction = new ThwackingAction(thwacker, comp, 1, 1000, threadManager, forwardAction);//fires the ball after forwardAction is executed and waits a second until the next action starts
        
        forwardAction.begin(); //starts moving robot forward for one second
        thwackingAction.begin();//begins thwackingAction once liftyAction is
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
        //initialize the threads
        thwackerThread = new ThwackerMappingThread(thwacker, comp, driverStation, 10, threadManager);
        driveThread = new TalonTankDriveMappingThread(driveBase, driverStation, 10, threadManager);
        liftyThread = new LiftyThingMappingThread(liftyThing, leadScrew, leadTop, leadBottom, liftTop, liftBottom, driverStation, 10, threadManager);
        compressorThread = new CompressorThread(comp, 10, threadManager);
        
        //start the threads
        thwackerThread.start();//starts thread for thwacker shooter
        driveThread.start();//starts thread for driving
        liftyThread.start();//starts thread for lifty thing
        compressorThread.start();
        System.out.println("Compressor should be working now...");

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
