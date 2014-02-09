package org.carobotics.logic;

import org.carobotics.hardware.Compressor;

/**
 *
 * @author CaRobotics
 */
public class CompressorThread extends RobotThread{

    Compressor comp;
    
    public CompressorThread(Compressor comp, int period, ThreadManager threadManager){
        super(period, threadManager);
        this.comp = comp;
    }
    
    protected void cycle() {
        if(!comp.getPressureSwitchValue()) {
        } else {
            comp.start();
            comp.stop();
        }
    }
    
    protected void onDestroy(){
        comp.stop();
    }
    
}
