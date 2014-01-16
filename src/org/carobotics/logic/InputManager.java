package org.carobotics.logic;

import java.util.Hashtable;
import java.util.Vector;

public class InputManager {
    private Hashtable axesValues = new Hashtable();
    
    public void addAxis(String name, double value, boolean analog, boolean relative) {
        Vector vec = new Vector();
        Vector vecValues = new Vector();
        if(relative && axesValues.containsKey(name)) {
            vecValues = (Vector) axesValues.get(name);
            vecValues.insertElementAt(new Double(value), 0);
            if(vecValues.size() > 10) {
                vecValues.setSize(10);
            }
        } else {
            vecValues.addElement(new Double(value));
        }
        vec.addElement(vecValues);
        vec.addElement(analog ? Boolean.TRUE : Boolean.FALSE);
        vec.addElement(relative ? Boolean.TRUE : Boolean.FALSE);
        axesValues.put(name, vec);
        System.out.println(name + ": " + getValues(name).firstElement() + " " + getAnalog(name) + " " + getRelative(name));
    }
    
    public Vector getValues(String name) {
        return (Vector) ((Vector) axesValues.get(name)).firstElement();
    }
    
    public boolean getAnalog(String name) {
        return ((Boolean) ((Vector) axesValues.get(name)).elementAt(1)).booleanValue();
    }
    
    public boolean getRelative(String name) {
        return ((Boolean) ((Vector) axesValues.get(name)).elementAt(2)).booleanValue();
    }
}
