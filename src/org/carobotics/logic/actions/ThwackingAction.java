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
import org.carobotics.subsystems.Thwacker;
/**
 *
 * @author Student
 */
public class ThwackingAction extends Action{ 
private Solenoid shooterOne, shootertwo;
public boolean isComplete = false;
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.shooterOne = shooter1;
        this.shootertwo = shooter2;
        System.out.println("thwacking action started");
    }
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependencies){
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        //this.shooterOne = shooter1;
        //this.shootertwo = shooter2;
        System.out.println("thwacking action started");
    }
    
    public boolean isComplete() {
       return isComplete;
    }

    protected void cycle() {
        
        }
    }

