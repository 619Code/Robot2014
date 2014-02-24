package org.carobotics.logic;

import org.carobotics.hardware.Compressor;

/**
 *
 * @author CaRobotics
 */
public class CompressorThread extends RobotThread{

    private Compressor comp;
    
    public CompressorThread(Compressor comp, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.comp = comp;
    }
    
    protected void cycle() {
        
        if(!comp.getPressureSwitchValue()) {
            System.out.println("too much pressure!! stopping compressor...");
            comp.stop();
        } else {
            //System.out.println("compressor is on");
            comp.start();
        }
        
    }
    
    protected void onDestroy(){
        comp.stop();
    }
    
}
