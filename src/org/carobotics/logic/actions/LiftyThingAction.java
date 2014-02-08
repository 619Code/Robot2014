/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic.actions;
import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.LiftyThing;
/**
 *
 * @author Student
 */
public class LiftyThingAction extends Action {
    public boolean isComplete = false;
    
    private long timeTarget;
    private LiftyThing liftyThing;
    private long startTime;
    
    public LiftyThingAction(LiftyThing liftyThing, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){
        super (waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.liftyThing = liftyThing;
        System.out.println("[LiftyThingAction] started");
    
    }
    public  LiftyThingAction(LiftyThing liftyThing, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency) {
        this(liftyThing, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }


    public boolean isComplete() {
        return isComplete;
    }

    public void begin() {
        startTime = System.currentTimeMillis();
        liftyThing.unlock();
    }

    protected void cycle() {
        if(System.currentTimeMillis()-startTime < 1000){
            liftyThing.getMotor().set(-0.3);
        }else{
            liftyThing.getMotor().set(0.0);
        }
    }
    
}
