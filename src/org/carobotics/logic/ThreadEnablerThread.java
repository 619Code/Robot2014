package org.carobotics.logic;

import org.carobotics.hardware.Joystick;

/**
 * @author CaRobotics
 */
public class ThreadEnablerThread extends RobotThread{
    RobotThread threadToEnable;
    Joystick joy;
    int button;

    public ThreadEnablerThread(int period, ThreadManager threadManager, RobotThread thread, Joystick joy, int button){
        super(period, threadManager);
        this.threadToEnable = thread;
        this.joy = joy;
        this.button = button;
    }
    
    protected void cycle() {
        if(joy.getButton(button)){
            threadToEnable.setSuspended(false);
        }else{
            threadToEnable.setSuspended(true);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        threadToEnable.setSuspended(true);
    }
    
    
    
    
}
