package org.carobotics.subsystems;

import org.carobotics.logic.actions.r2012.RampDepressorAction;
import org.carobotics.logic.actions.r2012.BallReleaseAction;
import org.carobotics.subsystems.r2012.BallManipulator;
import org.carobotics.subsystems.r2012.RampDepressor;
import edu.wpi.first.wpilibj.DigitalInput;
import java.util.Vector;
import org.carobotics.logic.r2012.LifterThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.*;

/**
 *
 * @author thomas
 */
public class AutonomousSelector {

    /*
     * What the robot will do at the very beginning of autonomous mode
     */
    public final static class StartMode {
        public final static int NUM_BITS = 1; // This is hardware dependent, you can't just change it
        public final static int NO_DELAY = 0;
        public final static int DELAY_5_SECOND = 1;
    }

    /*
     * Main autonomous code options
     */
    public final static class RunMode {
        public final static int NUM_BITS = 1; // This is hardware dependent, you can't just change it
        // Do nothing
        public final static int DO_NOTHING = 0;
        public final static int DO_SOMETHING = 1;
    }

    /*
     * Turn while moving or go straight
     */
    public final static class TurnMode {
        public final static int NUM_BITS = 1; // This is hardware dependent, you can't just change it
        public final static int STRAIGHT = 0;
        public final static int TURN = 1;
    }

    public final static class PIDMode {
        public final static int NUM_BITS = 1; // This is hardware dependent, you can't just change it
        public final static int ENABLED = 0;
        public final static int DISABLED = 1;
    }
    
    DigitalInput[] startSwitches;
    DigitalInput[] runSwitches;
    DigitalInput[] turnSwitches;
    DigitalInput[] PIDSwitches;

    public AutonomousSelector() {
        // We could pass in all of the digital input IDs, but I'd rather hard code it.

        // Create digital inputs here
        startSwitches = new DigitalInput[StartMode.NUM_BITS];
        runSwitches = new DigitalInput[RunMode.NUM_BITS];
        turnSwitches = new DigitalInput[TurnMode.NUM_BITS];
        PIDSwitches = new DigitalInput[TurnMode.NUM_BITS];

        int startChannel = 1;
        startChannel = addDigitalInputs(startSwitches, 1, startChannel);
        startChannel = addDigitalInputs(runSwitches, 1, startChannel);
        startChannel = addDigitalInputs(turnSwitches, 1, startChannel);
        startChannel = addDigitalInputs(PIDSwitches, 1, startChannel);

    }

    private int addDigitalInputs(DigitalInput[] inputsArray, int moduleNumber, int startChannel) {
        System.out.println("---------");
        for(int i = 0; i < inputsArray.length; i++) {
            System.out.println("Adding digital input: i=" + i + " channel=" + (startChannel + i));
            inputsArray[i] = new DigitalInput(moduleNumber, startChannel + i);
        }
        return startChannel + inputsArray.length;
    }

    public int getStartMode() {
        boolean[] bits = readBits(startSwitches);
        return bitsToInt(bits);
    }

    public int getRunMode() {
        boolean[] bits = readBits(runSwitches);
        return bitsToInt(bits);
    }

    public int getTurnMode() {
        boolean[] bits = readBits(turnSwitches);
        return bitsToInt(bits);
    }

    public boolean getPIDDefault() {
        boolean[] bits = readBits(PIDSwitches);
        return bitsToInt(bits) == 1;
    }

    private boolean[] readBits(DigitalInput[] inputsArray) {
        boolean[] bits = new boolean[inputsArray.length];
        for(int i = 0; i < inputsArray.length; i++) {
            DigitalInput di = (DigitalInput) inputsArray[i];
            bits[i] = !di.get(); // REVERSED
        }
        return bits;
    }

    private int bitsToInt(boolean[] bits) {
        int value = 0;
        for(int i = 0; i < bits.length; i++) {
            if(bits[i]) {
                value += pow(2, i);
            }
        }
        return value;
    }

    private int pow(int base, int exponent) {
        int value = 1;
        for(int i = 0; i < exponent; i++) {
            value *= base;
        }
        return value;
    }

    public ActionSequence getActionSequence(TankDriveBase driveBase, ThreadManager threadManager, double wheelCircumference, double gearRatio, double correctionRatio, FrisbeeDumper dumper) {
        ActionSequence seq = new ActionSequence();

        int start = getStartMode();
        int run = getRunMode();
        int turn = getTurnMode();
        boolean PID = getPIDDefault();

        switch(start) {
            case StartMode.NO_DELAY: // 0 Second Delay
                // No delay
                break;
            case StartMode.DELAY_5_SECOND: // 5 Second Delay
                seq.add(new DelayAction(100, 10, 5000, threadManager, new Vector()));
                break;
        }

        boolean enabled = false;

        switch(run) {
            case RunMode.DO_NOTHING:
                enabled = false;
                break;
            case RunMode.DO_SOMETHING:
                enabled = true;
                break;
        }

        if(enabled) {
            switch(turn) {
                case TurnMode.STRAIGHT:
                    seq.add(new TimeMovementAction(driveBase, 100, 10, 7000, 0.8, 0.8, threadManager, seq.getLastAction()));
                    break;
                case TurnMode.TURN:
                    if(PID) {
                        seq.add(new SpeedDistanceAction(driveBase, 60, 13, wheelCircumference, gearRatio, 3, threadManager, seq.getLastAction(), 100, 10));
                        seq.add(new TurnDistanceAction(driveBase, 60, 0.6, wheelCircumference, gearRatio, 0.2, threadManager, seq.getLastAction(), 100, 10));
                        seq.add(new TimeMovementAction(driveBase, 100, 10, 4000, 0.2, 0.2, threadManager, seq.getLastAction()));
                        
                    } else {
                        seq.add(new TimeMovementAction(driveBase, 100, 10, 4000, 0.2, 0.2, threadManager, seq.getLastAction()));
                        seq.add(new TurnAction(driveBase, 0.4, 1000, 200, 10, threadManager, seq.getLastAction()));
                        seq.add(new TimeMovementAction(driveBase, 100, 10, 4000, 0.2, 0.2, threadManager, seq.getLastAction()));
                    }
                    break;
            }
            seq.add(new DumperAction(dumper, 100, 10, threadManager, seq.getLast()));
        }

        return seq;
    }

    public String toString() {
        return "[AutonomousSelector] Start: " + getStartMode() + " Run: " + getRunMode() + " Turn: " + getTurnMode() + " PID: " + getPIDDefault();
    }
}
