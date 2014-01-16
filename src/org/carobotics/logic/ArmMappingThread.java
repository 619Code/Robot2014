package org.carobotics.logic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.Arms;
import org.carobotics.subsystems.FourStickDriverStation;

/*
 * @author carobotics
 */
public class ArmMappingThread extends RobotThread {
    private Arms arms;
    private GripperThread grippers;
    private FourStickDriverStation driverStation;
    private final static boolean DEBUG = false;
    // Potentiomter-based movement
    PIDController wRPID, wLPID, lPID, aPID;
    // Control modes
    private boolean allowOperatorControl = true;
    private boolean allowPidControl = true;
    // Times
    boolean handsStopped = true, feetStopped = true;
    // Constants
    private static final int JOINT_MODE_DISABLED = 0;
    private static final int JOINT_MODE_MANUAL = 1;
    private static final int JOINT_MODE_PID = 2;
    private static final int JOINT_WRIST_LEFT = 1;
    private static final int JOINT_WRIST_RIGHT = 2;
    private static final int JOINT_WRIST_BOTH = 3;
    private static final int JOINT_ARM = 4;
    private static final int JOINT_LEG = 5;
    private static final int JOINT_NONE = 6;
    // Debug

    public ArmMappingThread(Arms arms, GripperThread grippers, FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.arms = arms;
        this.grippers = grippers;
        this.driverStation = driverStation;

        setupPID(); // DO NOT REMOVE - This sets up each PID loop. Not doing this would be bad.
    }

    private void setupPID() {

        wRPID = new PIDController(0.0, 0.0, 0.0, arms.getRightWrist().getPotentiometer(), arms.getRightWrist());
        wLPID = new PIDController(0.0, 0.0, 0.0, arms.getLeftWrist().getPotentiometer(), arms.getLeftWrist());
        lPID = new PIDController(0.0, 0.0, 0.0, arms.getHips().getPotentiometer(), arms.getHips());
        aPID = new PIDController(0.0, 0.0, 0.0, arms.getShoulders().getPotentiometer(), arms.getShoulders());

        wRPID.setInputRange(0.0, 360.0);
        wLPID.setInputRange(0.0, 360.0);
        lPID.setInputRange(0.0, 360.0);
        aPID.setInputRange(0.0, 360.0);
        
        wRPID.setPercentTolerance(15); // TODO: Adjust these
        wLPID.setPercentTolerance(15);
        lPID.setPercentTolerance(15);
        aPID.setPercentTolerance(15);

        wRPID.setOutputRange(-0.1, 0.1);
        wLPID.setOutputRange(-0.1, 0.1);
        lPID.setOutputRange(-0.1, 0.1);
        aPID.setOutputRange(-0.1, 0.1);
        


        resetTargets();

        // Just in case...
        wRPID.disable();
        wLPID.disable();
        lPID.disable();
        aPID.disable();
    }

    protected void cycle() {


        if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON11)){
            setAllowPidControl(true);
        }
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.BUTTON10)){
            setAllowPidControl(false);
            grippers.setManualOnly(true);
        }
        
        // disable PID if on target
        if(wRPID.onTarget()) wRPID.disable();
        else wRPID.enable();
        if(wLPID.onTarget()) wLPID.disable();
        else wLPID.enable();
        if(lPID.onTarget()) lPID.disable();
        else lPID.enable();
        if(aPID.onTarget()) aPID.disable();
        else aPID.enable();
        

        if(allowOperatorControl) {
            boolean armManual=false, legManual=false, leftWristManual=false, rightWristManual=false;

            // Leg manual control
            if(getJoystickPressed34(Joystick.Button.BUTTON2)) {
                setLegMode(JOINT_MODE_MANUAL);
                arms.setHipsPercent(getJoystickY34(Joystick.Button.BUTTON2));
                setLegTargetAngle(arms.getHips().getAngle());
                legManual=true;
            }

            // Arm manual control
            if(getJoystickPressed34(Joystick.Button.TRIGGER)) {
                setArmMode(JOINT_MODE_MANUAL);
                arms.setShouldersPercent(getJoystickY34(Joystick.Button.TRIGGER));
                setArmTargetAngle(arms.getShoulders().getAngle());
                armManual=true;
            }

            // Wrists manual control
            if(getJoystickPressed34(Joystick.Button.BUTTON3)) {
                setWristModeBoth(JOINT_MODE_MANUAL);
                arms.setWristsPercent(getJoystickY34(Joystick.Button.BUTTON3));
                setWristLeftTargetAngle(arms.getLeftWrist().getAngle());
                setWristRightTargetAngle(arms.getRightWrist().getAngle());
                rightWristManual=true;
                leftWristManual=true;
            }

            // Left wrist manual control
            if(getJoystickPressed34(Joystick.Button.BUTTON4)) {
                setWristModeLeft(JOINT_MODE_MANUAL);
                arms.setLeftWristPercent(getJoystickY34(Joystick.Button.BUTTON4));
                setWristLeftTargetAngle(arms.getLeftWrist().getAngle());
                leftWristManual=true;

            }

            // Right wrist manual control
            if(getJoystickPressed34(Joystick.Button.BUTTON5)) {
                setWristModeRight(JOINT_MODE_MANUAL);
                arms.setRightWristPercent(getJoystickY34(Joystick.Button.BUTTON5));
                setWristRightTargetAngle(arms.getRightWrist().getAngle());
                rightWristManual=true;
            }
            
            if (!legManual){
                setLegMode(JOINT_MODE_PID);
            }
            
            if (!armManual){
                setArmMode(JOINT_MODE_PID);
            }
            
            if (!leftWristManual){
                setWristModeLeft(JOINT_MODE_PID);
            }
            
            if (!rightWristManual){
                setWristModeRight(JOINT_MODE_PID);
            }
            

        } 
        
        if(!handsStopped){
            handsStopped = true;
            grippers.getHands().stop();
        }
        
        // Hands
        boolean leftHandsBtn = driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON10);
        boolean rightHandsBtn = driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON11);
        if(leftHandsBtn || rightHandsBtn){
            handsStopped = false;
            grippers.setManualOnly(true);
            double scale = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_THROTTLE);
            if(scale<0.3) scale = 0.3;
            double input = driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y) * scale;
            if(leftHandsBtn){
                // manual left hand
                grippers.getHands().getLeftClaw().set(input);
            }
            
            if(rightHandsBtn){
                // manual right hand
                grippers.getHands().getRightClaw().set(input);
            }
        }else{ // Not using manual? Wimp. Ok, enable toggle.
            if(driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON9)) {
                grippers.setManualOnly(false);
                if(grippers.isFeetClosed()) {
                    if(grippers.getFeet().isClosed()){
                        grippers.openHands();
                    }else if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON11)){
                        grippers.openHands();
                    }else{
                        grippers.closeHands();
                    }
                } else {
                    grippers.closeHands();
                }
            }
        }
        
        if(!feetStopped){
            feetStopped = true;
            grippers.getFeet().stop();
        }
        
        // Feet toggle
        boolean leftFootBtn = driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON7);
        boolean rightFootBtn = driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON6);
        if(leftFootBtn || rightFootBtn){
            feetStopped = false;
            grippers.setManualOnly(true);
            double scale = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_THROTTLE);
            if(scale<0.3) scale = 0.3;
            double input = driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y) * scale;
            if(leftFootBtn){
                // manual left hand
                grippers.getFeet().getLeftClaw().set(input);
            }
            
            if(rightFootBtn){
                // manual right hand
                grippers.getFeet().getRightClaw().set(input);
            }
        }else{ // Not using manual? Wimp. Ok, enable toggle.
            if(driverStation.getThirdJoystick().getButton(Joystick.Button.BUTTON8)) {
                grippers.setManualOnly(false);
                if(grippers.isFeetClosed()) {
                    if(grippers.getHands().isClosed()){
                        grippers.openFeet();
                    }else if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON11)){
                        grippers.openFeet();
                    }else{
                        grippers.closeFeet();
                    }
                } else {
                    grippers.closeFeet();
                }
            }
        }

        if(DEBUG) {
            System.out.println("Shoulder PID: Setpoint = " + aPID.getSetpoint() + " Error = " + aPID.getError() + " Output = " + aPID.get() + " Enabled? " + aPID.isEnable());
        }
        
        // SmartDashboard
        
        // Gripper currents
        SmartDashboard.putNumber("Gripper - Hand Left - Current", grippers.getHands().getLeftClaw().getCurrent());
        SmartDashboard.putNumber("Gripper - Hand Right - Current", grippers.getHands().getRightClaw().getCurrent());
        SmartDashboard.putNumber("Gripper - Foot Left - Current", grippers.getFeet().getLeftClaw().getCurrent());
        SmartDashboard.putNumber("Gripper - Foot Right - Current", grippers.getFeet().getRightClaw().getCurrent());
        
        // Gripper limits
        SmartDashboard.putBoolean("Gripper - Hand Left - Open Limit", grippers.getHands().leftLimit());
        SmartDashboard.putBoolean("Gripper - Hand Right - Open Limit", grippers.getHands().rightLimit());
        SmartDashboard.putBoolean("Gripper - Foot Left - Open Limit", grippers.getFeet().leftLimit());
        SmartDashboard.putBoolean("Gripper - Foot Right - Open Limit", grippers.getFeet().rightLimit());
        
        SmartDashboard.putNumber("Joint - Leg - Angle", arms.getShoulders().getAngle());
        SmartDashboard.putNumber("Joint - Foot - Angle", arms.getHips().getAngle());
        SmartDashboard.putNumber("Joint - Left Wrist - Angle", arms.getLeftWrist().getAngle());
        SmartDashboard.putNumber("Joint - Right Wrist - Angle", arms.getRightWrist().getAngle());
    }

    protected void onDestroy() {
        // DO NOT REMOVE THIS CODE
        // THIS STOPS THE PID LOOP. WITHOUT THIS, BAD STUFF HAPPENS!

        // Killing method #1 (insurance policy)
        setWristPID(0.0, 0.0, 0.0);
        setArmsPID(0.0, 0.0, 0.0);
        setLegsPID(0.0, 0.0, 0.0);

        // Killing method #2 (correct way)
        wRPID.disable();
        wLPID.disable();
        lPID.disable();
        aPID.disable();

        // Kiling method #3: Firing squad
        // TODO: Implement firing squad
    }

    public void setWristPID(double p, double i, double d) {
        wLPID.setPID(p, i, d);
        wRPID.setPID(p, i, d);
    }

    public void setArmsPID(double p, double i, double d) {
        aPID.setPID(p, i, d);
    }

    public void setLegsPID(double p, double i, double d) {
        lPID.setPID(p, i, d);
    }

    public void setWristTargetAngle(double angle) {
        setWristLeftTargetAngle(angle);
        setWristRightTargetAngle(angle);
    }

    public void setWristLeftTargetAngle(double angle) {
        wLPID.setSetpoint(angle);
    }

    public void setWristRightTargetAngle(double angle) {
        wRPID.setSetpoint(angle);
    }

    public void setArmTargetAngle(double angle) {
        aPID.setSetpoint(angle);
    }

    public void setLegTargetAngle(double angle) {
        lPID.setSetpoint(angle);
    }

    public void resetTargets() {
        setWristLeftTargetAngle(arms.getLeftWrist().getAngle());
        setWristRightTargetAngle(arms.getRightWrist().getAngle());
        setLegTargetAngle(arms.getShoulders().getAngle());
        setArmTargetAngle(arms.getHips().getAngle());

    }

    private void setLegMode(int mode) {
        setMode(lPID, mode);
    }

    private void setArmMode(int mode) {
        setMode(aPID, mode);
    }

    private void setWristModeBoth(int mode) {
        setMode(wLPID, mode);
        setMode(wRPID, mode);
    }

    private void setWristModeLeft(int mode) {
        setMode(wLPID, mode);
    }

    private void setWristModeRight(int mode) {
        setMode(wRPID, mode);
    }

    /**
     * Sets all joints except the specified joint to the specified mode
     * @param mode Mode to set all joints except the specified one to
     * @param joint The one joint that shouldn't be changed
     */
    private void setModeAllExcept(int mode, int joint) {
        switch(joint) {
            case JOINT_ARM:
                setLegMode(mode);
                setWristModeBoth(mode);
                break;
            case JOINT_LEG:
                setArmMode(mode);
                setWristModeBoth(mode);
                break;
            case JOINT_WRIST_BOTH:
                setLegMode(mode);
                setArmMode(mode);
                break;
            case JOINT_WRIST_LEFT:
                setLegMode(mode);
                setArmMode(mode);
                setWristModeRight(mode);
                break;
            case JOINT_WRIST_RIGHT:
                setLegMode(mode);
                setArmMode(mode);
                setWristModeLeft(mode);
                break;
            case JOINT_NONE:
                setLegMode(mode);
                setArmMode(mode);
                setWristModeBoth(mode);
                break;
            default:
                System.out.println("[ArmMappingThread] Invalid joint passed to setModeAllExcept: " + mode);
                break;
        }
    }

    private void setMode(PIDController pid, int mode) {
        
        if (mode==JOINT_MODE_PID && !allowPidControl){
            mode=JOINT_MODE_DISABLED;
        }
        switch(mode) {
            case JOINT_MODE_DISABLED:
                disablePIDLoop(pid);
                break;
            case JOINT_MODE_MANUAL:
                disablePIDLoop(pid);
                break;
            case JOINT_MODE_PID:
                pid.enable();
                break;
            default:
                disablePIDLoop(pid);
                System.out.println("[ArmMappingThread] Invalid mode passed to setMode: " + mode);
                break;
        }
    }

    /**
     * Properly disable a PIDController. Call this when switching to manual control.
     * The PIDController class (WPI written) doesn't reset when you disable the
     * controller. It's fairly well written otherwise, though. :)
     * @param pid 
     */
    private void disablePIDLoop(PIDController pid) {
        pid.reset();
    }

    public Arms getArms() {
        return arms;
    }

    public boolean allowOperatorControl() {
        return allowOperatorControl;
    }

    public void setAllowOperatorControl(boolean allowOperatorControl) {
        this.allowOperatorControl = allowOperatorControl;
    }

    public boolean allowPidControl() {
        return allowPidControl;
    }

    public void setAllowPidControl(boolean allowPidControl) {
        this.allowPidControl = allowPidControl;
    }

    /**
     * Returns the Y axis on whichever joystick the specified button is pushed
     * on. If it is pushed on both joysticks, the fourth joystick gets priority.
     * @param button The button by which the joystick will be detected
     * @return The value of the axis
     */
    public double getJoystickY34(int button) {

        double scalePercent = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Z);
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }

        if(driverStation.getFourthJoystick().getButton(button)) {
            return driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        } else if(driverStation.getThirdJoystick().getButton(button)) {
            return driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        } else {
            return 0.0;
        }
    }

    /**
     * Returns true if the specified button is pressed on either the third or
     * fourth joystick.
     * @param button The button to be checked (i.e. Joystick.Button.BUTTON4)
     * @return True if the button is pressed on either joystick
     */
    public boolean getJoystickPressed34(int button) {
        return driverStation.getThirdJoystick().getButton(button) || driverStation.getFourthJoystick().getButton(button);
    }

    public PIDController getArmPID() {
        return aPID;
    }

    public PIDController getLegPID() {
        return lPID;
    }

    public PIDController getWristLeftPID() {
        return wLPID;
    }

    public PIDController getWristRightPID() {
        return wRPID;
    }
    
    
}
