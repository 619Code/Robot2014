package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;

/**
 * @author CaRobotics
 */
public class DelayAction extends Action{
    
    private long delayTarget;
    
    public DelayAction(int waitForDependenciesPeriod, int runPeriod, int delay, ThreadManager threadManager, Vector dependencies){
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        delayTarget = delay + System.currentTimeMillis();
        System.out.println("[DelayAction] Delay target: "+delayTarget);
    }

    public boolean isComplete() {
        System.out.println("[DelayAction] Is complete? " + (System.currentTimeMillis() >= delayTarget));
        return System.currentTimeMillis() >= delayTarget;
    }

    protected void cycle() {
        System.out.println("DelayAction cycle");
        if(System.currentTimeMillis() >= delayTarget){
            stopRunning();
            System.out.println("[DelayAction] Finished at time = " + System.currentTimeMillis());
        }
    }
    
}
