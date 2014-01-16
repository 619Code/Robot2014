package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.r2012.LifterThread;
import org.carobotics.logic.ThreadManager;

/**
 * @author carobotics
 */
public class LiftMovementAction extends Action {

    private LifterThread lifter;
    private int targetLevel;
    private boolean isComplete = false;
    
    private long timeoutTime = 0;
    private long timeoutLength = 0;

    public LiftMovementAction(LifterThread lifter, int targetLevel, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.lifter = lifter;
        this.targetLevel = targetLevel;
        if(lifter == null){
            isComplete = true;
            super.isRunning = false;
        }
    }
    
    public LiftMovementAction(LifterThread lifter, int targetLevel, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Action dependency) {
        this(lifter, targetLevel, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        addDependency(dependency);
    }
    
    public void setTimeout(long millis){
        this.timeoutLength = millis;
    }

    public boolean isComplete() {
        
        if(timeoutTime > 0){
            if(timeoutTime > System.currentTimeMillis()){
                lifter.setMode(LifterThread.LifterMode.MANUAL);
                isComplete = true;
            }
        }
        
        return isComplete;
    }

    protected void begin() {
        if(timeoutLength > 0) timeoutTime = System.currentTimeMillis() + timeoutLength;
        lifter.setMode(targetLevel);
    }

    protected void cycle() {
        switch(targetLevel){
            case LifterThread.LifterMode.DO_NOTHING:
                isComplete = true;
                break;
            case LifterThread.LifterMode.MANUAL:
                isComplete = true;
                break;
            case LifterThread.LifterMode.MOVE_TO_BOTTOM:
                if(lifter.getLifter().getBottomLimit()) isComplete = true;
                break;
            case LifterThread.LifterMode.MOVE_TO_TOP:
                if(lifter.getLifter().getUpperLimit() && lifter.getLifter().getUpperSmallPieceDetector()){
                    isComplete = true;
                    System.out.println("Setting LiftMovement complete!");
                }
                break;
            case LifterThread.LifterMode.MOVE_TO_LEVEL_ONE:
                // We really don't have a great way to detect this for sure.
                // There's a level one limit, but there's a chance of the lifter
                // being off by just a bit and missing it, so to be safe:
                isComplete = true;
                break;
            default:
                System.out.println("[LiftMovementAction] Unknown LifterMode used: "+targetLevel);
                break;
        }
    }
    
}
