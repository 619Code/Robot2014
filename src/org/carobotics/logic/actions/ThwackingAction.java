/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.logic.actions;
import org.carobotics.hardware.Solenoid;
import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.logic.actions.Action;
/**
 *
 * @author Student
 */
public class ThwackingAction extends Action{ 
private Solenoid shooterOne, shooter2;
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.shooterOne = shooter1;
        this.shooter2 = shooter2;
        System.out.println("thwacking action started");
    }
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependencies){
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.shooterOne = shooter1;
        this.shooter2 = shooter2;
        System.out.println("thwacking action started");
    }
    
    public boolean isComplete() {
       return isComplete;
    }

    protected void cycle() {
        throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
