package org.carobotics.logic.actions.r2012;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.Action;
import org.carobotics.subsystems.r2012.BallManipulator;

/**
 *
 * @author carobotics
 */
public class BallReleaseAction extends Action {

    private BallManipulator ballManipulator;
    private int runTimeMillis;
    private long endTime;
    private boolean isComplete = false;

    public BallReleaseAction(BallManipulator ballManipulator, int runTimeMillis, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.ballManipulator = ballManipulator;
        this.runTimeMillis = runTimeMillis;
        if(ballManipulator == null){
            isComplete = true;
            super.isRunning = false;
        }
    }
    
    public BallReleaseAction(BallManipulator ballManipulator, int runTimeMillis, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Action dependency) {
        this(ballManipulator, runTimeMillis, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        ballManipulator.lowerRails();
        ballManipulator.getMotor().set(-1.0);
        endTime = System.currentTimeMillis() + runTimeMillis;
    }

    protected void cycle() {
        if(System.currentTimeMillis() > endTime){
            isComplete = true;
        }
    }
    
}
