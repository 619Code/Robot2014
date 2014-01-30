/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.carobotics.main;


import edu.wpi.first.wpilibj.IterativeRobot;
import org.carobotics.hardware.Potentiometer;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.Arms;
import org.carobotics.subsystems.FourStickDriverStation;
import org.carobotics.logic.ArmMappingThread;
import org.carobotics.subsystems.GripperPair;
import org.carobotics.logic.GripperThread;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TestRobot extends IterativeRobot {
     // Robot Systems (stuff from org.carobotics.subsystems)
    FourStickDriverStation driver; // sets up all components to be made into objects
    Arms arms;
    GripperPair feet;
    GripperPair hands;
    // Thread Manager
    ThreadManager threadManager = new ThreadManager();
    // Logic Threads (stuff from org.carobotics.logic)
    ArmMappingThread armThread;
    GripperThread gripperThread;
    Potentiometer potentiometer;
    
   
   

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        System.out.println("\n"); //printout to prove workability
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2013 Test Robot                              //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG        
        
        
        driver = new FourStickDriverStation(1, 2, 3, 4);
        
//        Potentiometer shoulderPot = new Potentiometer(1, 0.0, 0.0, 5.0, 360.0);
//        arms = new Arms(1, shoulderPot, 2, shoulderPot, 3, shoulderPot, 4, shoulderPot);
//        feet = new GripperPair(2, 3, 6, 7);
//        hands = new GripperPair(4, 5, 8, 9);
                
    }

    public void autonomousInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
    }
    
    public void teleopInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
        
//        gripperThread = new GripperThread(feet, hands, 100, threadManager);
//        armThread = new ArmMappingThread(arms, gripperThread, driver, 10, threadManager);
//        armThread.setArmsPID(0.01, 0.0, 0.0);
//        gripperThread.start();
//        armThread.start();
    }
    
    public void teleopPeriodic() {
//        System.out.println("Shoulder pot: " + arms.getShoulders().getAngle());
//        
//        System.out.println("left joystick: " + driver.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y));
//        System.out.println("right joystick: " + driver.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y));
//        System.out.println("third joystick: " + driver.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y));
//        System.out.println("fourth joystick: " + driver.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y));
        driver.getRightJoystick().debugPrint();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
      
    }
        

    public void disabledInit() {
        threadManager.killAllThreads(); // DO NOT REMOVE!!!
    }    
}
