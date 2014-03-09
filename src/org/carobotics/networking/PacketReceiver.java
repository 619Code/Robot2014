package org.carobotics.networking;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.ServerSocketConnection;
import org.carobotics.logic.RemoteProcessedCamera;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;

public class PacketReceiver extends RobotThread {
    
    private DataInputStream dataIn = null;
    private ServerSocketConnection ssc;
    private RemoteProcessedCamera cam;

    public PacketReceiver(int period, ThreadManager threadManager, ServerSocketConnection ssc, RemoteProcessedCamera cam) {
        super(period, threadManager);
        this.ssc = ssc;
        this.cam = cam;
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
                cam.setHotGoal(dataIn.readInt());
//                input.addAxis(dataIn.readUTF(), dataIn.readDouble(), dataIn.readBoolean(),  dataIn.readBoolean());
            } catch (IOException ex) {
                ex.printStackTrace();
                dataIn = null;
            }
        }
    }
}
