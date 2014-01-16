package org.carobotics.logic.actions;

import java.util.Vector;

/**
 * @author CaRobotics
 */
public class ActionSequence {
    
    private Vector actions;
    private boolean started = false;
    
    public ActionSequence() {
        actions = new Vector();
    }
    
    public Action add(Action action) {
        actions.addElement(action);
        return action;
    }
    
    public void start() {
        if(!started) {
            for(int i = 0; i < actions.size(); i++) {
                ((Action)actions.elementAt(i)).start();
            }
        }
        started = true;
    }
    
    public void stop() {
        for(int i = 0; i < actions.size(); i++) {
            ((Action)actions.elementAt(i)).stopRunning();
        }
    }
    
    public Action getLastAction() {
        if(actions.isEmpty()) return null;
        return (Action)actions.elementAt(actions.size()-1);
    }
    
    public Action getLast() {
        return getLastAction();
    }
    
    public int numberOfActions(){
        return actions.size();
    }
    
    public boolean isRunning(){
        if(!started) return false;
        for(int i = 0; i < actions.size(); i++) {
            if(((Action)actions.elementAt(i)).isRunning()) return true;
        }
        return false;
    }
}
