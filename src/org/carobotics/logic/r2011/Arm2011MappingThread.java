package org.carobotics.logic.r2011;

import org.carobotics.hardware.Joystick;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;
import org.carobotics.subsystems.r2011.Arm2011;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 * @author CaRobotics
 */
public class Arm2011MappingThread extends RobotThread {

    private Arm2011 arm;
    private FourStickDriverStation driverStation;

    public Arm2011MappingThread(Arm2011 arm, FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.arm = arm;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        moveUpper(driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y));
        moveLower(driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y));
        
        if(driverStation.getFourthJoystick().getButton(Joystick.Button.TRIGGER)){
            System.out.println("Claw triggered");
            arm.getClawSolenoid().set(true);
        }else{
            arm.getClawSolenoid().set(false);
        }
    }

    public void moveUpper(double inpt){
        
        // Check arm limits
        
        if(!arm.getArmUpper().get()){
            
            if(inpt < 0){
                inpt = 0;
                System.out.println("Arm at upper limit.");
            }
        }
        
        if(!arm.getArmLower().get()){
            if(inpt > 0){
                inpt = 0;
                System.out.println("Arm at lower limit.");
            }
        }
        
        if(inpt>0.5) inpt = 0.5;
        if(inpt<-0.5) inpt = -0.5;
        
        arm.getTopMotor().set(inpt);
    }
    
    public void moveLower(double inpt){
        
        // Check arm limits
        
        arm.getLowerMotor().set(inpt);
    }
}
