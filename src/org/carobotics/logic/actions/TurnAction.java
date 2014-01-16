package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.TankDriveBase;

/**
 * @author CaRobotics
 */
public class TurnAction extends Action {
    private TankDriveBase driveBase;
    private double speed;
    private long timeMillis;
    private long startTime;
    private boolean isComplete = false;

    public TurnAction(TankDriveBase driveBase, double speed, long timeMillis, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.driveBase = driveBase;
        this.speed = speed;
        this.timeMillis = timeMillis;
    }

    public TurnAction(TankDriveBase driveBase, double speed, long timeMillis, int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager, Action dependency) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependency);
        this.driveBase = driveBase;
        this.speed = speed;
        this.timeMillis = timeMillis;
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        startTime = System.currentTimeMillis();
    }

    protected void cycle() {
        if((System.currentTimeMillis() - startTime) > timeMillis) {
            driveBase.drive(0, 0);
            isComplete = true;
        } else {
            driveBase.drive(speed, -speed);
        }
    }
}
