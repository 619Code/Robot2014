package org.carobotics.logic;

import org.carobotics.hardware.Compressor;

/**
 *
 * @author CaRobotics
 */
public class CompressorThread extends RobotThread{

    Compressor compressor;
    
    public CompressorThread(int period, ThreadManager threadManager, Compressor compressor){
        super(period, threadManager);
        this.compressor = compressor;
    }
    
    protected void cycle() {
        if(!compressor.getPressureSwitchValue()) {
            compressor.start();
        } else {
            compressor.stop();
        }
    }
    
    protected void onDestroy(){
        compressor.stop();
    }
    
}
