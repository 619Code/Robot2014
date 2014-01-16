package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.hardware.Talon;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.TankDriveBase;

/**
 * @author CaRobotics
 */
public class TalonTimeDriveAction extends Action {
    private long timeTarget;
    private double percentSpeed;
    private Talon leftTalon, rightTalon;
    private int timeMillis;
    public boolean isComplete = false;

    public TalonTimeDriveAction(Talon left, Talon right, int waitForDependenciesPeriod, int runPeriod, int timeMillis, double percentSpeed, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.timeMillis = timeMillis;
        this.percentSpeed = percentSpeed;
        this.leftTalon = left;
        this.rightTalon = right;
        System.out.println("[TalonTimeDriveAction] Time target: " + timeTarget);
    }

    public TalonTimeDriveAction(Talon left, Talon right, int waitForDependenciesPeriod, int runPeriod, int timeMillis, double percentSpeed, ThreadManager threadManager, Action dependency) {
        this(left, right, waitForDependenciesPeriod, runPeriod, timeMillis, percentSpeed, threadManager, new Vector());
        super.addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        timeTarget = timeMillis + System.currentTimeMillis();
    }

    protected void cycle() {
        
        if(System.currentTimeMillis() >= timeTarget) {
            leftTalon.set(0.0);
            rightTalon.set(0.0);
            isComplete = true;
            stopRunning();
            System.out.println("[TalonTimeDriveAction] Finished at time = " + System.currentTimeMillis());
        }else{
            leftTalon.set(percentSpeed);
            rightTalon.set(percentSpeed);
        }
    }
}
