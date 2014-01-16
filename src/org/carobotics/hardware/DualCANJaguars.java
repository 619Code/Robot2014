package org.carobotics.hardware;

/**
 * @author thomas
 */
public class DualCANJaguars extends CANJaguar {

    CANJaguar slaveJaguar;

    public DualCANJaguars(int masterId, int slaveId) {
        super(masterId);
        slaveJaguar = new CANJaguar(slaveId);
    }

    public boolean setPercent(double percent) {
        slaveJaguar.setPercent(percent);

        return super.setPercent(percent);
    }

    public boolean setSpeed(double speed) {
        System.out.println("[DualCANJaguars] Speed mode is not currently supported for two Jaguars");
        try {
            slaveJaguar.setMode(CANJaguar.ControlMode.DISABLED);
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
        return super.setSpeed(speed);
    }
    
    public boolean setPosition(double position) {
        System.out.println("[DualCANJaguars] Position mode is not currently supported for two Jaguars");
        try {
            slaveJaguar.setMode(CANJaguar.ControlMode.DISABLED);
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
        return super.setSpeed(position);
    }
    
    public void setReversed(boolean r){
        super.setReversed(r);
        slaveJaguar.setReversed(r);
    }
}
