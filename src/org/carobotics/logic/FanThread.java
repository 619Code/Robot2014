package org.carobotics.logic;

import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class FanThread extends RobotThread {
    protected FourStickDriverStation driverStation;
    protected CANJaguar fan;
    
    protected double lastFanVal = 0.0;
    
    public FanThread(FourStickDriverStation driverStation, CANJaguar fan, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.driverStation = driverStation;
        this.fan = fan;
    }

    protected void cycle() {
        double newFanVal = driverStation.getFourthJoystick().getAxis(Joystick.Axis.AXIS_Y);
        if(newFanVal < 0){
            fan.set(0.0);
            lastFanVal = 0.0;
        }else if(newFanVal > (lastFanVal + 0.05)){
            fan.set(lastFanVal + 0.05);
            lastFanVal = lastFanVal + 0.05;
        }else{
            fan.set(newFanVal);
            lastFanVal = newFanVal;
        }
                
        
    }
    
    protected void onDestroy(){
        fan.set(0.0);
        lastFanVal = 0.0;
    }
}
