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
import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.Potentiometer;
import org.carobotics.logic.ActionsMappingThread;
import org.carobotics.logic.ArmMappingThread;
import org.carobotics.logic.DumperMappingThread;
import org.carobotics.logic.GripperThread;
import org.carobotics.logic.PIDTuningThread;
import org.carobotics.logic.TankDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.DumperAction;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.subsystems.Arms;
import org.carobotics.subsystems.AutonomousSelector;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.FrisbeeDumper;
import org.carobotics.subsystems.GripperPair;
import org.carobotics.subsystems.TankDriveBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013 extends IterativeRobot {
    // Robot Systems (stuff from org.carobotics.subsystems)
    TankDriveBase driveBase;
    FourStickDriverStation driverStation;
    GripperPair feet, hands;
    Arms arms;
    Potentiometer shoulders, hips, leftWrist, rightWrist;
    FrisbeeDumper dumper;
    AutonomousSelector autonomousSelector;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    TankDriveMappingThread driveThread;
    GripperThread gripperThread;
    ArmMappingThread armThread;
    DumperMappingThread dumperThread;
    ActionsMappingThread actionsThread;
    // Actions
    TimeMovementAction forwardAction;
    TimeMovementAction backwardAction;
    DumperAction dumperAction;

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
        driveBase = new TankDriveBase(2, 3);//2L,3R
        driveBase.getLeftJaguar().setReversed(true);

        driverStation = new FourStickDriverStation(1, 2, 3, 4);
        driverStation.getRightJoystick().setReversed(Joystick.Axis.AXIS_Y, true);

        feet = new GripperPair(4, 5, 5, 6);
        hands = new GripperPair(6, 7, 7, 8);

        //TODO: Calibrate
        shoulders = new Potentiometer(1, 5, 0, 0, 360);
        hips = new Potentiometer(2, 5, 0, 0, 360);
        leftWrist = new Potentiometer(3, 5, 0, 0, 360);
        rightWrist = new Potentiometer(4, 5, 0, 0, 360);
        arms = new Arms(1, shoulders, 2, hips, 3, leftWrist, 4, rightWrist);

        dumper = new FrisbeeDumper(5, 6, 9);

        autonomousSelector = new AutonomousSelector();
        
        System.out.println(autonomousSelector);
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!

        autonomousSelector.getActionSequence(driveBase, threadManager, 1.05, 10.0, 1.0, dumper).start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!

        System.out.println(autonomousSelector); // DEBUG
        
        
        // DRIVE
        driveThread = new TankDriveMappingThread(driveBase, driverStation, 10, threadManager);
        driveThread.start();
        
        // DUMP
        dumperThread = new DumperMappingThread(dumper, driverStation, 30, threadManager);
        dumperThread.start(); // TODO: Enable once driveThread is confrimed working
        
        // CLIMB
        gripperThread = new GripperThread(feet, hands, 10, threadManager);
        gripperThread.setManualOnly(!autonomousSelector.getPIDDefault());
        gripperThread.start(); // TODO: Enable once dumperThread is confirmed working

        armThread = new ArmMappingThread(arms, gripperThread, driverStation, 10, threadManager);
        armThread.setAllowPidControl(autonomousSelector.getPIDDefault()); // TODO: Enable once autonomous selector is confirmed working
        //armThread.setAllowPidControl(false); // TODO: Remove once above line is enabled AND PID loops have been tuned
        armThread.start(); // TODO: Enable once dumperThread is confirmed working

        // AUTOMATE CLIMBING
        actionsThread = new ActionsMappingThread(armThread, gripperThread, driverStation, 30, threadManager);
        actionsThread.start(); // TODO: Enable once everything else is working AND PID loops have been tuned
        
      
        
        
        
        // DEBUG -- PID TUNING
        // PID tuning tool
        if(false) { // TODO: KEEP THIS FALSE FOR ALL ACTUAL COMPETITIONS OR ELSE YOU CANNOT DRIVE
            driveThread.setSuspended(true); // Pause drive thread, we're taking over joystick 1
            PIDTuningThread pidTuningThread = new PIDTuningThread(driveBase, armThread, driverStation, 80, threadManager);
            pidTuningThread.start();
        }
    }

    public void teleopPeriodic() {
        
        // Joint calibration tool
        if (false) { // TODO: Make this false for actual competition
            System.out.println("JOINT CALIBRATION: Shoulder V: " + arms.getShoulders().getPotentiometer().getRaw() + " Deg: " + arms.getShoulders().getAngle()
                    + " Hips V: " + arms.getHips().getPotentiometer().getRaw() + " Deg: " + arms.getHips().getAngle()
                    + " Left Wrist V: " + arms.getLeftWrist().getPotentiometer().getRaw() + " Deg: " + arms.getLeftWrist().getAngle()
                    + " Right Wrist V: " + arms.getRightWrist().getPotentiometer().getRaw() + " Deg: " + arms.getRightWrist().getAngle());
        }

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
