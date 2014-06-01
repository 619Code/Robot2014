/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.carobotics.hardware.DualInputSolenoid;
import org.carobotics.hardware.Compressor;
import org.carobotics.hardware.Talon;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.hardware.Servo;
import org.carobotics.hardware.Relay;
import org.carobotics.logic.LiftyThingMappingThread;
import org.carobotics.logic.ThwackerMappingThread;
import org.carobotics.logic.TalonTankDriveMappingThread;
import org.carobotics.logic.CompressorThread;
import org.carobotics.logic.RemoteProcessedCamera;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.logic.actions.ThwackingAction;
import org.carobotics.logic.actions.LiftyThingAction;
import org.carobotics.networking.Networking;
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
    Talon leftDrive, rightDrive;
    Talon leadScrew;
    Talon lifter;
    Relay compRelay;
    Servo camera;
    DigitalInput leadTop, leadBottom;
    DigitalInput liftTop, liftBottom;
    DigitalInput pressureSwitch;
    Thwacker thwacker;
    LiftyThing liftyThing;
    TalonDriveBase driveBase;
    FourStickDriverStation driverStation;
    DualInputSolenoid shooter1, shooter2;
    DualInputSolenoid bleedAir1, bleedAir2;
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
    // Networking
    Networking network;
    RemoteProcessedCamera cam;
    
    
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
        
        //driver station
        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        
        //plug into digital output on the digital sidecar
        leadScrew = new Talon(1);
        rightDrive = new Talon(2);
        leftDrive = new Talon(3);
        lifter = new Talon(4);
        
        
        
        //plug into digital input on the digital sidecar
        leadTop = new DigitalInput(14);
        leadBottom = new DigitalInput(1);
        liftTop = new DigitalInput(3);
        liftBottom = new DigitalInput(4);
        pressureSwitch = new DigitalInput(5);
        camera = new Servo(6);
        
        //plug into pneumatics bumper
//        shooter1 = new DualInputSolenoid(1, 2);
//        shooter2 = new DualInputSolenoid(3, 4); 
//        bleedAir1 = new DualInputSolenoid(5, 6);
//        bleedAir2 = new DualInputSolenoid(7, 8);
        
        //plugs into relay section of digital sidecar
        compRelay = new Relay(1);
        
        //subsystems
        comp = new Compressor(pressureSwitch, compRelay);
        driveBase = new TalonDriveBase(leftDrive, rightDrive);
        //thwacker = new Thwacker(camera, shooter1, shooter2, bleedAir1, bleedAir2);
        liftyThing = new LiftyThing(lifter, leadScrew, leadTop, leadBottom, liftTop, liftBottom);
        
        //cam = new RemoteProcessedCamera();
    }

    public void autonomousInit() {
        
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
          //network = new Networking(threadManager, 40, cam);
        //if you have something not from org.carobotics.logic.actions in here, then you're doing it wrong!!
        //you'll crash the bot when it changes from autonomous to tele-op
        forwardAction = new TimeMovementAction(driveBase, 1, 1000, 1000, 1, 1, threadManager);//moves forward 1 second
        //thwackingAction = new ThwackingAction(thwacker, 1, 1000, threadManager, forwardAction);//fires the ball after forwardAction is executed and waits a second until the next action starts
        //compressorThread = new CompressorThread(comp, 10, threadManager);
          //network.startThreads();
        forwardAction.begin(); //starts moving robot forward for one second
        //thwackingAction.begin();//begins thwackingAction once liftyAction is
        //compressorThread.start();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
        //initialize the threads
        //thwackerThread = new ThwackerMappingThread(thwacker, comp, driverStation, 10, threadManager);
        driveThread = new TalonTankDriveMappingThread(driveBase, driverStation, 10, threadManager);
        liftyThread = new LiftyThingMappingThread(liftyThing, driverStation, 10, threadManager);
        compressorThread = new CompressorThread(comp, 10, threadManager);
        
        //start the threads
        //thwackerThread.start();//starts thread for thwacker shooter
        driveThread.start();//starts thread for driving
        liftyThread.start();//starts thread for lifty thing
        compressorThread.start();

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
