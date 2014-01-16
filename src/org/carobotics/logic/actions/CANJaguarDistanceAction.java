package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.hardware.CANJaguar;
import org.carobotics.logic.ThreadManager;

/**
 * Runs a specific motor a specific linear distance (using the circumference of
 * the wheel to convert rotations to linear distance)
 *
 * @author CaRobotics
 */
public class CANJaguarDistanceAction extends Action {

    private double errorMarginDistance;
    private double targetDistance;
    private double wheelCircumference;
    private double gearRatio;
    private CANJaguar jaguar;
    private double targetRotations;
    private double errorMarginRotations;
    private boolean isComplete = false;

    /**
     * 
     * @param jaguar Jaguar to use (CAN)
     * @param distance Linear distance (feet) to travel
     * @param wheelCircumference Circumference of the drive wheel in feet
     * @param gearRatio Gear ratio to wheel, expressed as input/output
     * @param errorMargin Linear distance error margin to determine completion
     * @param threadManager The ThreadManager
     * @param dependencies List of Actions that this Action is dependent on
     * @param waitForDependenciesPeriod Period for the thread when it is waiting for its dependencies to be satisfied
     * @param runningPeriod Period for the thread when it is actively running
     */
    public CANJaguarDistanceAction(CANJaguar jaguar, double distance,
            double wheelCircumference, double gearRatio, double errorMargin,
            ThreadManager threadManager, Vector dependencies,
            int waitForDependenciesPeriod, int runningPeriod) {
        super(waitForDependenciesPeriod, runningPeriod, threadManager);
        this.errorMarginDistance = errorMargin;
        this.targetDistance = distance;
        this.wheelCircumference = wheelCircumference;
        this.jaguar = jaguar;
        this.gearRatio = gearRatio;
    }
    
    /**
     * 
     * @param jaguar Jaguar to use (CAN)
     * @param distance Linear distance (feet) to travel
     * @param wheelCircumference Circumference of the drive wheel in feet
     * @param gearRatio Gear ratio to wheel, expressed as input/output
     * @param errorMargin Linear distance error margin to determine completion
     * @param threadManager The ThreadManager
     * @param dependency Action that this Action is dependent on
     * @param waitForDependenciesPeriod Period for the thread when it is waiting for its dependencies to be satisfied
     * @param runningPeriod Period for the thread when it is actively running
     */
    public CANJaguarDistanceAction(CANJaguar jaguar, double distance,
            double wheelCircumference, double gearRatio, double errorMargin,
            ThreadManager threadManager, Action dependency,
            int waitForDependenciesPeriod, int runningPeriod) {
        this(jaguar, distance, wheelCircumference, gearRatio, errorMargin, threadManager, new Vector(), waitForDependenciesPeriod, runningPeriod);
        addDependency(dependency);
    }

    private void findTargetRotations() {
        targetRotations = (targetDistance / wheelCircumference) * wheelCircumference * gearRatio;
        errorMarginRotations = Math.abs(errorMarginDistance / wheelCircumference) * wheelCircumference * gearRatio;
        System.out.println("[MotorDistanceAction] Current Position: " + jaguar.getPosition() + " | Target Rotations: " + targetRotations + " | Error Margin: " + errorMarginRotations);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        findTargetRotations();
        System.out.println("[MotorDistanceAction] Moving jaguar from " + jaguar.getPosition() + " to " + targetRotations);
        jaguar.setPosition(targetRotations);
    }

    protected void cycle() {
        if ((jaguar.getPosition() <= (targetRotations + errorMarginRotations))
                && (jaguar.getPosition() >= (targetRotations - errorMarginRotations))) {
            isComplete = true;
        }

        //jaguar.setPosition(targetRotations);

        System.out.println("[MotorDistanceAction] Jaguar position: " + jaguar.getPosition());

        if (isComplete) {
            jaguar.setPercent(0);
            stopRunning();
            System.out.println("[MotorDistanceAction] Complete at position " + jaguar.getPosition());
        }
    }
}
