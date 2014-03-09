/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.carobotics.hardware.Talon;
import org.carobotics.hardware.DigitalInput;
import org.carobotics.hardware.Servo;
import org.carobotics.logic.LiftyThingMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.LiftyThingAction;
import org.carobotics.subsystems.LiftyThing;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 *
 * The robot for this year has two manipulators, the liftyThing, for picking up
 * a 2 ft diameter ball off the ground and a thwacker that hits/shoots the ball
 * 
 * @author Student
 */
public class Robot2014LiftyThingOnly extends IterativeRobot{
    
    // Robot Systems (stuff from org.carobotics.subsystems)
    Talon leadScrew;
    Talon lifter;
    Servo camera;
    DigitalInput leadTop, leadBottom;
    DigitalInput liftTop, liftBottom;
    LiftyThing liftyThing;
    FourStickDriverStation driverStation;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    LiftyThingMappingThread liftyThread;
    // Actions
    LiftyThingAction liftyAction;
    
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("\n");// shows code is working
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Cavalier Robotics                     TEAM 619   //");
        System.out.println("// 2014 LiftyThing Code                             //");
        System.out.println("//////////////////////////////////////////////////////\n");

        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG
        
        //driver station
        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        
        //plug into digital output on the digital sidecar
        leadScrew = new Talon(4);
        lifter = new Talon(1);
        
        
        
        //plug into digital input on the digital sidecar
        leadTop = new DigitalInput(1);
        leadBottom = new DigitalInput(2);
        liftTop = new DigitalInput(3);
        liftBottom = new DigitalInput(4);
        camera = new Servo(6);
        
        //subsystems
        liftyThing = new LiftyThing(lifter, leadScrew, leadTop, leadBottom, liftTop, liftBottom); 
        
    }

    public void autonomousInit() {
        
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        //if you have something not from org.carobotics.logic.actions in here, then you're doing it wrong!!
        //you'll crash the bot when it changes from autonomous to tele-op
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
        System.out.println("teleop initiated");
        
        //initialize the threads
        liftyThread = new LiftyThingMappingThread(liftyThing, driverStation, 10, threadManager);
        
        //start the threads
        liftyThread.start();//starts thread for lifty thing
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
