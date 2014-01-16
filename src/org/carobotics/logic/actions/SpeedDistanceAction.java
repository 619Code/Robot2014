package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.InvalidConfigurationException;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.TankDriveBase;

/**
 * Runs a specific motor a specific linear distance (using the circumference of
 * the wheel to convert rotations to linear distance)
 *
 * @author CaRobotics
 */
public class SpeedDistanceAction extends Action {

    private double errorMarginDistance;
    private double targetDistance;
    private double wheelCircumference;
    private double gearRatio;
    private CANJaguar leftJaguar, rightJaguar;
    
    private double errorMarginRotations;
    private double speedRPM;
    
    private long timeoutLength = 0;
    private long timeoutTime = 0;
    
    private class MotorInfo{
        public double targetRotations;
        public boolean isComplete = false;
        public long lastPositionCheckTime = 0;
        public double lastPosition = 0;
        public boolean reverseDirection = false;
    }
    
    private MotorInfo leftMotorInfo, rightMotorInfo;

    /**
     * 
     * @param driveBase Drive base of the robot
     * @param speedRPM Speed to run the wheels
     * @param distance Linear distance (feet) to travel
     * @param wheelCircumference Circumference of the drive wheel in feet
     * @param gearRatio Gear ratio to wheel, expressed as input/output
     * @param errorMargin Linear distance error margin to determine completion
     * @param threadManager The ThreadManager
     * @param dependencies List of Actions that this Action is dependent on
     * @param waitForDependenciesPeriod Period for the thread when it is waiting for its dependencies to be satisfied
     * @param runningPeriod Period for the thread when it is actively running
     */
    public SpeedDistanceAction(TankDriveBase driveBase, double speedRPM, double distance,
            double wheelCircumference, double gearRatio, double errorMargin,
            ThreadManager threadManager, Vector dependencies,
            int waitForDependenciesPeriod, int runningPeriod) {
        super(waitForDependenciesPeriod, runningPeriod, threadManager);
        this.errorMarginDistance = errorMargin;
        this.targetDistance = distance;
        this.wheelCircumference = wheelCircumference;
        this.leftJaguar = driveBase.getLeftJaguar();
        this.rightJaguar = driveBase.getRightJaguar();
        this.gearRatio = gearRatio;
        this.leftMotorInfo = new MotorInfo();
        this.rightMotorInfo = new MotorInfo();
        this.speedRPM = speedRPM;
        if(this.targetDistance < 0){
            leftMotorInfo.reverseDirection = true;
            rightMotorInfo.reverseDirection = true;
        }
    }
    
    /**
     * 
     * @param driveBase Drive base of the robot
     * @param speedRPM Speed to run the wheels
     * @param distance Linear distance (feet) to travel
     * @param wheelCircumference Circumference of the drive wheel in feet
     * @param gearRatio Gear ratio to wheel, expressed as input/output
     * @param errorMargin Linear distance error margin to determine completion
     * @param threadManager The ThreadManager
     * @param dependency Action that this Action is dependent on
     * @param waitForDependenciesPeriod Period for the thread when it is waiting for its dependencies to be satisfied
     * @param runningPeriod Period for the thread when it is actively running
     */
    public SpeedDistanceAction(TankDriveBase driveBase, double speedRPM, double distance,
            double wheelCircumference, double gearRatio, double errorMargin,
            ThreadManager threadManager, Action dependency,
            int waitForDependenciesPeriod, int runningPeriod) {
        this(driveBase, speedRPM, distance, wheelCircumference, gearRatio, errorMargin, threadManager, new Vector(), waitForDependenciesPeriod, runningPeriod);
        addDependency(dependency);
    }
    
    public void setTimeoutLength(long millis){
        this.timeoutLength = millis;
    }

    private void findTargetRotations() {
        leftMotorInfo.targetRotations = leftJaguar.getPosition() + (targetDistance / wheelCircumference) * gearRatio;
        rightMotorInfo.targetRotations = rightJaguar.getPosition() + (targetDistance / wheelCircumference) * gearRatio;
        errorMarginRotations = Math.abs(errorMarginDistance / wheelCircumference) * gearRatio;
        System.out.println("[SpeedDistanceAction] Current Position: L - " + leftJaguar.getPosition() + " R - " + rightJaguar.getPosition() + " | Target Rotations: L - " + leftMotorInfo.targetRotations + " R - " + rightMotorInfo.targetRotations + " | Error Margin: " + errorMarginRotations);
    }

    public boolean isComplete() {
        boolean timeUp = false;
        if(timeoutTime > 0) timeUp = System.currentTimeMillis() > timeoutTime;
        return leftMotorInfo.isComplete || rightMotorInfo.isComplete || timeUp;
    }

    protected void begin() {
        try {
            leftJaguar.setMode(CANJaguar.ControlMode.SPEED);
            rightJaguar.setMode(CANJaguar.ControlMode.SPEED);
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
            leftMotorInfo.isComplete = true;
            rightMotorInfo.isComplete = true;
            stopRunning();
        }
        if(timeoutLength > 0) timeoutTime = System.currentTimeMillis() + timeoutLength;
        findTargetRotations();
        updateLastPosition();
    }
    
    private void updateLastPosition(){
        leftMotorInfo.lastPosition = leftJaguar.getPosition();
        leftMotorInfo.lastPositionCheckTime = System.currentTimeMillis();
        rightMotorInfo.lastPosition = rightJaguar.getPosition();
        rightMotorInfo.lastPositionCheckTime = System.currentTimeMillis();
    }

    protected void cycle() {
        // Check if we're done
        if ((leftJaguar.getPosition() <= (leftMotorInfo.targetRotations + errorMarginRotations))
                && (leftJaguar.getPosition() >= (leftMotorInfo.targetRotations - errorMarginRotations))) {
            leftMotorInfo.isComplete = true;
        }
        if ((rightJaguar.getPosition() <= (rightMotorInfo.targetRotations + errorMarginRotations))
                && (rightJaguar.getPosition() >= (rightMotorInfo.targetRotations - errorMarginRotations))) {
            rightMotorInfo.isComplete = true;
        }
        
        // Check if we're going the wrong direction
        if(System.currentTimeMillis() - leftMotorInfo.lastPositionCheckTime > 300){
            // Left
            double rotationsToTarget = Math.abs(leftJaguar.getPosition() - leftMotorInfo.targetRotations);
            double prevRotationsToTarget = Math.abs(leftMotorInfo.lastPosition - leftMotorInfo.targetRotations);
            System.out.println("Rotations to target: "+rotationsToTarget + " | Prev Rotations to Target: "+prevRotationsToTarget);
            if(rotationsToTarget > prevRotationsToTarget){
                System.out.println("[SpeedDistanceAction] [ATTN] Switching direction of left jaguar!");
                leftMotorInfo.reverseDirection = ! leftMotorInfo.reverseDirection;
            }
            
            // Right
            rotationsToTarget = Math.abs(rightJaguar.getPosition() - rightMotorInfo.targetRotations);
            prevRotationsToTarget = Math.abs(rightMotorInfo.lastPosition - rightMotorInfo.targetRotations);
            if(rotationsToTarget > prevRotationsToTarget){
                System.out.println("[SpeedDistanceAction] [ATTN] Switching direction of right jaguar!");
                rightMotorInfo.reverseDirection = ! rightMotorInfo.reverseDirection;
            }
            
            updateLastPosition();
        }

        // Tell us where we are
        System.out.println("[SpeedDistanceAction] Jaguar position: L - " + leftJaguar.getPosition() + " R - " + rightJaguar.getPosition());

        // If we're done, stop!
        if (isComplete()) {
            leftJaguar.setPercent(0);
            rightJaguar.setPercent(0);
            stopRunning();
            System.out.println("[SpeedDistanceAction] Complete at position L - " + leftJaguar.getPosition() + " R - " + rightJaguar.getPosition());
            return;
        }
        
        // Ok, finally, let's go!
        if(leftMotorInfo.reverseDirection){
            leftJaguar.setSpeed(-speedRPM);
        }else{
            leftJaguar.setSpeed(speedRPM);
        }
        
        if(rightMotorInfo.reverseDirection){
            rightJaguar.setSpeed(-speedRPM);
        }else{
            rightJaguar.setSpeed(speedRPM);
        }
    }
}
