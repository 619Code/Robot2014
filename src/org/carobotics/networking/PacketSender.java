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
    
    private void send(NameValuePacket packet) {
        String sendData = replace("\t", "\\t", packet.name) + "\t" + replace("\t", "\\t", packet.value);
        try {
            out = ssc.acceptAndOpen().openDataOutputStream();
            out.writeUTF(sendData);
            out.flush();
            queued.removeElement(packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void queue(String name, String value) {
        queued.addElement(new NameValuePacket(name, value));
    }
    
    protected void cycle() {
        for(int i = 0; i < queued.size(); i++) {
            NameValuePacket packet = (NameValuePacket)queued.elementAt(i);
            send(packet);
        }
    }
    
    private static String replace(String needle, String replacement, String haystack) {
        String result;
        int index = haystack.indexOf(needle);
        if (index == 0) {
            result = replacement + haystack.substring(needle.length());
            return replace(needle, replacement, result);
        } else if (index > 0) {
            result = haystack.substring(0, index) + replacement + haystack.substring(index + needle.length());
            return replace(needle, replacement, result);
        } else {
            return haystack;
        }
    }
    
    private class NameValuePacket {
        public String name;
        public String value;
        
        public NameValuePacket(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
