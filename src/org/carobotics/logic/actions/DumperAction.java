package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.FrisbeeDumper;

/**
 * @author CaRobotics
 */
public class DumperAction extends Action {
    private long timeTarget;
    private FrisbeeDumper dumper;
    public boolean isComplete = false;
    private long startTime;

    public DumperAction(FrisbeeDumper dumper, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.dumper = dumper;
        System.out.println("[DumperAction] Started");
    }

    public DumperAction(FrisbeeDumper dumper, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency) {
        this(dumper, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        startTime = System.currentTimeMillis();
        dumper.unlock();
    }

    protected void cycle() {
        if(System.currentTimeMillis()-startTime < 1000){
            dumper.getMotor().set(-0.3);
        }else{
            dumper.getMotor().set(0.0);
        }
    }
}
