/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carobotics.networking;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import org.carobotics.logic.InputManager;
import org.carobotics.logic.ThreadManager;

/**
 *
 * @author CaRobotics
 */
public class Networking {
    
    public PacketSender sender;
    public PacketReceiver receiver;
    
    public Networking(ThreadManager manager, int period, InputManager inputs) {
        ServerSocketConnection ssc = null;
        try {
            ssc = (ServerSocketConnection) Connector.open("socket://:1735");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        sender = new PacketSender(ssc, period, manager);
        receiver = new PacketReceiver(period, manager, ssc, inputs);
    }
    
    public void startThreads() {
        sender.start();
        receiver.start();
    }
    
    public void queue(String name, String value) {
        sender.queue(name, value);
    }
}
