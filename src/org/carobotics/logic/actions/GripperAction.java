package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.GripperThread;
import org.carobotics.logic.ThreadManager;

/**
 * @author CaRobotics
 */
public class GripperAction extends Action {
    public static class Grippers {
        public final static int HANDS = 0;
        public final static int FEET = 1;
    }
    
    private GripperThread grippers;
    private int gripperPair;
    private boolean closed;
    public boolean isComplete = false;

    public GripperAction(GripperThread grippers, int waitForDependenciesPeriod, int runPeriod, int gripperPair, boolean closed, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.grippers = grippers;
        this.gripperPair = gripperPair;
        this.closed = closed;
        
    }

    public GripperAction(GripperThread grippers, int waitForDependenciesPeriod, int runPeriod, int gripperPair, boolean closed, ThreadManager threadManager, Action dependency) {
        this(grippers, waitForDependenciesPeriod, runPeriod, gripperPair, closed, threadManager, new Vector());
        super.addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        System.out.println("[GripperAction] Started at time = " + System.currentTimeMillis() + " for gripper pair " + gripperPair);
    }

    protected void cycle() {
        switch(gripperPair) {
            case Grippers.HANDS:
                grippers.closeHands();
                if(grippers.isHandsClosed() && closed) {
                    isComplete = true;
                } else if(!grippers.isHandsClosed() && !closed) {
                    isComplete = true;
                }
                break;
            case Grippers.FEET:
                grippers.closeFeet();
                if(grippers.isFeetClosed() && closed) {
                    isComplete = true;
                } else if(!grippers.isFeetClosed() && !closed) {
                    isComplete = true;
                }
                break;
        }
        
        if(isComplete) {
            stopRunning();
            System.out.println("[GripperAction] Finished at time = " + System.currentTimeMillis());
        }
    }
}
