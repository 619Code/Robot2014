package org.carobotics.networking;

import java.io.DataOutputStream;
import java.io.IOException;
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
    
    public void stop() {
        try {
            ssc.close();
            ssc = null;
        } catch (Exception e) { }
    }
    
    public void stopRunning() {
        super.stopRunning();
        stop();
    }
    
    public void onDestroy() {
        super.onDestroy();
        stop();
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
        if(ssc != null) {
            for(int i = 0; i < queued.size(); i++) {
                send((byte[])queued.elementAt(i));
            }
        }
    }
}
