/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic.actions;
import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.Thwacker;
import org.carobotics.hardware.Compressor;
/**
 *
 * @author student 
 */
public class ThwackingAction extends Action{ 
private Thwacker thwacker;
public boolean isComplete = false;
private long startTime;

    public ThwackingAction(Thwacker thwacker, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){// defines variables 
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.thwacker = thwacker;
        System.out.println("[ThwackingAction] started");// Tells if it is working
    }
    public ThwackingAction(Thwacker thwacker, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency){
        this(thwacker, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }
    
    public boolean isComplete() {
       return isComplete;
    }
    
    public void begin() {
        startTime = System.currentTimeMillis();
        thwacker.fire();
    }

    protected void cycle() {
        thwacker.fire();
    }
}

