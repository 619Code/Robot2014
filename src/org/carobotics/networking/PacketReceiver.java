package org.carobotics.networking;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.ServerSocketConnection;
import org.carobotics.logic.InputManager;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;

public class PacketReceiver extends RobotThread {
    InputManager input;
    DataInputStream dataIn = null;
    ServerSocketConnection ssc;
    

    public PacketReceiver(int period, ThreadManager threadManager, ServerSocketConnection ssc, InputManager input) {
        super(period, threadManager);
        this.input = input;
        this.ssc = ssc;
    }

    protected void cycle() {
        if(dataIn == null) {
            try {
                dataIn = ssc.acceptAndOpen().openDataInputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
                dataIn = null;
            }
        }
        if(dataIn != null) {
            try {
                input.addAxis(dataIn.readUTF(), dataIn.readDouble(), dataIn.readBoolean(),  dataIn.readBoolean());
            } catch (IOException ex) {
                ex.printStackTrace();
                dataIn = null;
            }
        }
    }
}
