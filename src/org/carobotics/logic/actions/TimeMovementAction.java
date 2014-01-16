package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.TankDriveBase;

/**
 * @author CaRobotics
 */
public class TimeMovementAction extends Action {
    private long timeTarget;
    private double leftPercentSpeed, rightPercentSpeed;
    private TankDriveBase driveBase;
    private int timeMillis;
    public boolean isComplete = false;

    public TimeMovementAction(TankDriveBase driveBase, int waitForDependenciesPeriod, int runPeriod, int timeMillis, 
            double leftPercentSpeed, double rightPercentSpeed, ThreadManager threadManager, Action dependency) {
        this(driveBase, waitForDependenciesPeriod, runPeriod, timeMillis, leftPercentSpeed, rightPercentSpeed, threadManager);
        super.addDependency(dependency);
    }
    
    public TimeMovementAction(TankDriveBase driveBase, int waitForDependenciesPeriod, int runPeriod, int timeMillis, 
            double leftPercentSpeed, double rightPercentSpeed, ThreadManager threadManager) {
        this(driveBase, waitForDependenciesPeriod, runPeriod, timeMillis, leftPercentSpeed, rightPercentSpeed, threadManager, new Vector());
    }

    public TimeMovementAction(TankDriveBase driveBase, int waitForDependenciesPeriod, int runPeriod, int timeMillis, 
            double leftPercentSpeed, double rightPercentSpeed, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.timeMillis = timeMillis;
        this.leftPercentSpeed = leftPercentSpeed;
        this.rightPercentSpeed = rightPercentSpeed;
        this.driveBase = driveBase;
        System.out.println("[TimeMovementAction] Time target: " + timeTarget);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        timeTarget = timeMillis + System.currentTimeMillis();
    }

    protected void cycle() {
        driveBase.drive(leftPercentSpeed, rightPercentSpeed);
        if(System.currentTimeMillis() >= timeTarget) {
            driveBase.drive(0.0, 0.0);
            isComplete = true;
            stopRunning();
            System.out.println("[TimeMovementAction] Finished at time = " + System.currentTimeMillis());
        }
    }
}
