/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.main;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.carobotics.hardware.Talon;
import org.carobotics.logic.RobotTriangleDriveMappingThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.TimeMovementAction;
import org.carobotics.networking.Networking;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.subsystems.RobotTriangleBase;
/**
 *
 * @author Student
 */
public class TriangleRobot2014 {
    
    FourStickDriverStation driverStation;
    RobotTriangleBase driveBase;
    ThreadManager threadManager = new ThreadManager();
    RobotTriangleDriveMappingThread driveThread;
    TimeMovementAction forwardAction;
    Networking network;
    Talon leftDrive, rightDrive, backDrive;
    
    
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
        
        rightDrive = new Talon(1);
        leftDrive = new Talon(2);
        backDrive = new Talon(3);
        
        driveBase = new RobotTriangleBase(leftDrive, rightDrive, backDrive);
    }
    
    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
        //initialize the threads
        //thwackerThread = new ThwackerMappingThread(thwacker, comp, driverStation, 10, threadManager);
        driveThread = new RobotTriangleDriveMappingThread(driveBase, driverStation, 10, threadManager);
        
        //start the threads
        //thwackerThread.start();//starts thread for thwacker shooter
        driveThread.start();//starts thread for driving

    }
    
    public void disabledInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }
}
