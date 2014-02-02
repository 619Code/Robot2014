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
 * @author student 
 */
public class ThwackingAction extends Action{ 
private Solenoid shooterOne, shootertwo;// Solenoid because pneumatics
public boolean isComplete = false;
private long startTime;
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies){// defines variables 
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.shooterOne = shooter1;  // creates objects for talons
        this.shootertwo = shooter2;
        System.out.println("thwacking action started");// Tells if it is working
    }
    public ThwackingAction(Solenoid shooter1, Solenoid shooter2, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency){
        this(shooter1, shooter2, waitForDependenciesPeriod, runPeriod, threadManager, new Vector());
        super.addDependency(dependency);
    }
    
    public boolean isComplete() {
       return isComplete;
    }
    
    protected void begin() {
        startTime = System.currentTimeMillis();
        shooterOne.set(false);
        shootertwo.set(false);
    }

    protected void cycle() {
        
        }
    }

