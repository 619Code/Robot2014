/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.hardware.Relay;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.Action;
import org.carobotics.subsystems.FrisbeeDumper;

/**
 *
 * @author Student
 */
public class RelayAction extends Action{
    private long timeTarget;
    private Relay relay;
    public boolean isComplete = false;
    private long startTime;

    public RelayAction(Relay relay, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.relay = relay;
        System.out.println("[RelayAction] Started");
    }

    public RelayAction(Relay relay, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency) {
        this(relay, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void begin() {
        startTime = System.currentTimeMillis();
        relay.setForward();
    }

    protected void cycle() {
//        if(System.currentTimeMillis()-startTime < 1000){
//            relay.setOn();
//        }else{
//            relay.setOff();
//        }
        relay.setOn();
    }
}
