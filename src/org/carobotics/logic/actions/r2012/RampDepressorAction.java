package org.carobotics.logic.actions.r2012;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.Action;
import org.carobotics.subsystems.r2012.RampDepressor;

/**
 * @author CaRobotics
 */
public class RampDepressorAction extends Action {

    private RampDepressor rampDepressor;
    private boolean extend;
    private boolean isComplete = false;

    public RampDepressorAction(RampDepressor rampDepressor, boolean extend, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.rampDepressor = rampDepressor;
        this.extend = extend;
    }
    
    public RampDepressorAction(RampDepressor rampDepressor, boolean extend, int waitForDependenciesPeriod, 
            int runPeriod, ThreadManager threadManager, Action dependency) {
        this(rampDepressor, extend, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        if (extend) {
            rampDepressor.extendArm();
        } else {
            rampDepressor.retractArm();
        }
        isComplete = true;
    }

    protected void cycle() {
    }
    
}
