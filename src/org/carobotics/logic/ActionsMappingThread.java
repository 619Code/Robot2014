package org.carobotics.logic;

import java.util.Vector;
import org.carobotics.hardware.Joystick;
import org.carobotics.logic.actions.ActionSequence;
import org.carobotics.logic.actions.GripperAction;
import org.carobotics.logic.actions.JointAngleAction;
import org.carobotics.logic.actions.JointAngleAction.Joint;
import org.carobotics.logic.actions.GripperAction.Grippers;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class ActionsMappingThread extends RobotThread {
    protected FourStickDriverStation driverStation;
    private final static boolean DEBUG = false;
    private ActionSequence beg, climbFirst, collect, tango;
    private ThreadManager threadManager;
    private ArmMappingThread armMappingThread;
    private GripperThread gripperThread;

    public ActionsMappingThread(
            ArmMappingThread armMappingThread, GripperThread gripperThread,
            FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.driverStation = driverStation;
        this.armMappingThread = armMappingThread;
        this.threadManager = threadManager;
        this.gripperThread = gripperThread;

        // init actions
        initBeg();
        initClimbFirst();
        initCollect();
    }

    protected void cycle() {
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON10)) { // cancel all actions
            beg.stop();
            climbFirst.stop();
            collect.stop();
        }

        if(!(beg.isRunning() || climbFirst.isRunning() || collect.isRunning())) { // if none of them are running
            if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON11)) { // if override is pressed
                if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON6)) {
                    beg.start();
                } else if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON7)) {
                    climbFirst.start();
                } else if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON10)) {
                    collect.start();
                }

            }
        }
    }

    private void initBeg() {
        beg = new ActionSequence();
        beg.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, new Vector())); //Tilt up
        beg.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 150, threadManager, beg.getLast())); //Swing the arms past the end position
        beg.add(new JointAngleAction(armMappingThread, 100, 10, Joint.WRISTS, 59 + 85, threadManager, beg.getLast())); //Align the wrists with the final position
        beg.add(new GripperAction(gripperThread, 100, 10, Grippers.HANDS, false, threadManager, beg.getLast())); //Open the hands
        beg.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 143, threadManager, beg.getLast())); //Position the hands at the bar
        beg.add(new GripperAction(gripperThread, 100, 10, Grippers.HANDS, true, threadManager, beg.getLast())); //Close the hands
    }

    private void initClimbFirst() {
        climbFirst = new ActionSequence();
        climbFirst.add(new JointAngleAction(armMappingThread, 100, 10, Joint.WRISTS, 150, threadManager, new Vector())); //Pull Koko of the ground
        climbFirst.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 20, threadManager, climbFirst.getLast())); //Swing the body to the bar
        climbFirst.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 112, threadManager, climbFirst.getLast())); //Swing the legs into position
        climbFirst.add(new JointAngleAction(armMappingThread, 100, 10, Joint.WRISTS, 248, threadManager, climbFirst.getLast())); //Rotate the body up
        climbFirst.add(new GripperAction(gripperThread, 100, 10, Grippers.FEET, false, threadManager, climbFirst.getLast())); //Open the feet
        climbFirst.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 3, threadManager, climbFirst.getLast())); //Swing the feet to the bar
        climbFirst.add(new GripperAction(gripperThread, 100, 10, Grippers.FEET, true, threadManager, climbFirst.getLast())); //Close the feet
    }

    private void initCollect() {
        collect = new ActionSequence();
        collect.add(new GripperAction(gripperThread, 100, 10, Grippers.FEET, false, threadManager, new Vector())); //Open the feet
        collect.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 10, threadManager, collect.getLast())); //Pull the feet away from the bar
        collect.add(new JointAngleAction(armMappingThread, 100, 10, Joint.WRISTS, 150, threadManager, collect.getLast())); //Pull Koko away from the bar
        collect.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 20, threadManager, collect.getLast())); //Swing the legs into position
        collect.add(new JointAngleAction(armMappingThread, 100, 10, Joint.WRISTS, 248, threadManager, collect.getLast())); //Rotate the body up
        collect.add(new JointAngleAction(armMappingThread, 100, 10, Joint.SHOULDERS, 3, threadManager, collect.getLast())); //Swing the feet to the bar
        collect.add(new GripperAction(gripperThread, 100, 10, Grippers.FEET, true, threadManager, collect.getLast())); //Close the feet
    }

    private void initTango() {
        tango = new ActionSequence();
        tango.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, new Vector())); //tiltBack
        tango.add(new GripperAction(gripperThread, 100, 10, Grippers.HANDS, false, threadManager, tango.getLast())); //openHands1
        tango.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, tango.getLast())); //tiltForward
        tango.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, tango.getLast())); //reachPast
        tango.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, tango.getLast())); //angleHands
        tango.add(new JointAngleAction(armMappingThread, 100, 10, Joint.HIPS, 68, threadManager, tango.getLast())); //alignArms
        tango.add(new GripperAction(gripperThread, 100, 10, Grippers.HANDS, true, threadManager, tango.getLast())); //closeHands1
    }
}
