package org.carobotics.networking;

import java.io.DataOutputStream;
import java.util.Vector;
import javax.microedition.io.ServerSocketConnection;
import org.carobotics.logic.RobotThread;
import org.carobotics.logic.ThreadManager;

/**
 * @author carobotics
 */
public class PacketSender extends RobotThread {
    
    private Vector queued;
    private ServerSocketConnection ssc;
    private DataOutputStream out;
    
    public PacketSender(ServerSocketConnection ssc, int period, ThreadManager threadManager) {
        super(period, threadManager);
        queued = new Vector();
        this.ssc = ssc;
    }
    
    private void send(byte[] data) {
        if(data == null) {
            return;
        }
        try {
            out = ssc.acceptAndOpen().openDataOutputStream();
            out.write(data);
            out.flush();
            queued.removeElement(data);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void queue(byte[] data) {
        queued.addElement(data);
    }
    
    protected void cycle() {
        for(int i = 0; i < queued.size(); i++) {
            send((byte[])queued.elementAt(i));
        }
    }
}
