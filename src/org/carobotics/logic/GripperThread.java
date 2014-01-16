package org.carobotics.logic;

import org.carobotics.subsystems.GripperPair;

/**
 * @author CaRobotics
 */
public class GripperThread extends RobotThread {
    protected GripperPair feet, hands;
    private boolean feetClosed, handsClosed;
    private long feetStateChangeTime = 0, handsStateChangeTime = 0;
    private final static int CHANGE_DELAY = 100;
    boolean manualControl = false;
    
    public GripperThread(GripperPair feet, GripperPair hands, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.feet = feet;
        feetClosed = true;
        this.hands = hands;
        handsClosed = true;
        
        closeFeet();
        closeHands();
    }

    public boolean isFeetClosed() {
        return feetClosed;
    }

    public boolean isHandsClosed() {
        return handsClosed;
    }

    protected void cycle() {
        if(!manualControl){
            if(feetClosed && !feet.isClosed()) {
                feet.close();
            } else if(!feetClosed && feet.isClosed()) {
                feet.open();
            } else {
                feet.stop();
            }

            if(handsClosed && !hands.isClosed()) {
                hands.close();
            } else if(!handsClosed && hands.isClosed()) {
                hands.open();
            } else {
                hands.stop();
            }
        }
    }

    protected void onDestroy() {
        
    }

    public void closeFeet() {
        if(System.currentTimeMillis() - feetStateChangeTime < CHANGE_DELAY) return;
        if(!feetClosed){
            feetStateChangeTime = System.currentTimeMillis();
        }
        feetClosed = true;
    }

    public void closeHands() {
        if(System.currentTimeMillis() - handsStateChangeTime < CHANGE_DELAY) return;
        if(!handsClosed){
            handsStateChangeTime = System.currentTimeMillis();
        }
        handsClosed = true;
    }
    
    public void openFeet() {
        if(System.currentTimeMillis() - feetStateChangeTime < CHANGE_DELAY) return;
        if(feetClosed){
            handsStateChangeTime = System.currentTimeMillis();
        }
        feetClosed = false;
    }

    public void openHands() {
        if(System.currentTimeMillis() - handsStateChangeTime < CHANGE_DELAY) return;
        if(handsClosed){
            handsStateChangeTime = System.currentTimeMillis();
        }
        handsClosed = false;
    }
    
    public GripperPair getFeet(){
        return feet;
    }
    
    public GripperPair getHands(){
        return hands;
    }
    
    public void setManualOnly(boolean manual){
        this.manualControl = manual;
        
        if(manualControl){
            hands.stop();
            feet.stop();
        }
    }
}
