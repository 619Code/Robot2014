/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.main;

import org.carobotics.subsystems.FourStickDriverStation;

/**
 *
 *
 * @author Student
 */
import org.carobotics.logic.ThreadManager;
public class pneumaticsTesting {
    public void robotInit() {

        System.out.println("\n"); //printout to prove workability
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("//     Cavalier Robotics                 TEAM 619   //");
        System.out.println("//     2014 pneumatics testing                      //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        // Create all robot subsystems (i.e. stuff from org.carobotics.subsystems)
        // If you are creating something not from org.carobotics.subsystems, YER DOING IT WRONG        
        
        
       
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
