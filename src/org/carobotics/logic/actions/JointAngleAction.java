package org.carobotics.logic.actions;

import java.util.Vector;
import org.carobotics.logic.ArmMappingThread;
import org.carobotics.logic.ThreadManager;

/**
 * @author CaRobotics
 */
public class JointAngleAction extends Action {
    public static class Joint {
        public final static int SHOULDERS = 0;
        public final static int HIPS = 1;
        public final static int WRISTS = 2;
    }
    
    private ArmMappingThread armMap;
    private double angle;
    private int joint;
    public boolean isComplete = false;

    public JointAngleAction(ArmMappingThread armMap, int waitForDependenciesPeriod, int runPeriod, int joint, double angle, ThreadManager threadManager, Vector dependencies) {
        super(waitForDependenciesPeriod, runPeriod, threadManager, dependencies);
        this.armMap = armMap;
        this.angle = angle;
        this.joint = joint;
        
    }

    public JointAngleAction(ArmMappingThread armMap, int waitForDependenciesPeriod, int runPeriod, int joint, double angle, ThreadManager threadManager, Action dependency) {
        this(armMap, waitForDependenciesPeriod, runPeriod, joint, angle, threadManager, new Vector());
        super.addDependency(dependency);
    }

    public boolean isComplete() {
        return isComplete;
    }

    protected void begin() {
        System.out.println("[JointAngleAction] Started at time = " + System.currentTimeMillis() + " for joint " + joint);
    }

    protected void cycle() {
        switch(joint) {
            //TODO: make wiggle room
            case Joint.SHOULDERS:
                armMap.setArmTargetAngle(angle);
                if(armMap.getArms().getShoulders().getAngle() == angle) {
                    isComplete = true;
                }
                break;
            case Joint.HIPS:
                armMap.setArmTargetAngle(angle);
                if(armMap.getArms().getHips().getAngle() == angle) {
                    isComplete = true;
                }
                break;
            case Joint.WRISTS:
                armMap.setWristTargetAngle(angle);
                if(armMap.getArms().getLeftWrist().getAngle() == angle && armMap.getArms().getRightWrist().getAngle() == angle) {
                    isComplete = true;
                }
                break;
        }
        
        if(isComplete) {
            stopRunning();
            System.out.println("[JointAngleAction] Finished at time = " + System.currentTimeMillis());
        }
    }
}
