/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic.actions;
import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.Action;
import org.carobotics.subsystems.FrisbeeDumper;
/**
 *
 * @author Student
 */
public class LiftyThingAction extends Action {
    public boolean isComplete = false;
    
    public LiftyThingAction(int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){
        super (waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        System.out.println("Thwacking action started");
    
    }
    public  LiftyThingAction(int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency) {
        this(waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }


    public boolean isComplete() {
        return isComplete;
    }

    


    protected void cycle() {
    }
    
}
