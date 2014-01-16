package org.carobotics.hardware;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * @author CaRobotics
 */
import edu.wpi.first.wpilibj.CANJaguar.PositionReference;
import edu.wpi.first.wpilibj.CANJaguar.SpeedReference;

public class CANJaguar {
    private final static boolean DEBUG = true;

    public static class ControlMode {
        public final static int PERCENT = 0;
        public final static int SPEED = 1;
        public final static int POSITION = 2;
        public final static int DISABLED = 3;
    }
    
    private int controlMode = ControlMode.PERCENT;
    private int encoderCodesPerRevolution = 360; // This should be right for the US Digital E4P
    private double speedP, speedI, speedD; // speed mode PID constants
    private double positionP, positionI, positionD; // position mode PID constants
    private boolean isReversed = false;
    private double lastPositionSetpoint = 0, lastSpeedSetpoint = 0;
    private edu.wpi.first.wpilibj.CANJaguar jag;
    private int id;
    private boolean jaguarSuccessfullyCreated;

    public CANJaguar(int id) {
        this.id = id;
        jaguarSuccessfullyCreated = false;
        try {
            jag = new edu.wpi.first.wpilibj.CANJaguar(id);
            jaguarSuccessfullyCreated = true;
        } catch(CANTimeoutException ex) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            ex.printStackTrace();
        }

        // Percent mode by default
        controlMode = ControlMode.PERCENT;
    }

    public void setReversed(boolean r) {
        isReversed = r;
    }

    public boolean set(double percent) {
        return setPercent(percent);
    }

    public boolean setPercent(double percent) {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return false;
        }

        try {
            if(!jag.getControlMode().equals(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kPercentVbus)) {
                setMode(ControlMode.PERCENT);
            }
        } catch(Exception ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        if(isReversed) {
            percent = -percent;
        }

        try {
            jag.setX(percent);
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setSpeed(double speed) {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return false;
        }

        try {
            if(!jag.getControlMode().equals(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kSpeed)) {
                setMode(ControlMode.SPEED);
            }
        } catch(Exception ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        if(isReversed) {
            speed = -speed;
        }

        if(DEBUG) {
            System.out.println("[CANJaguar] Setting speed to " + speed);
        }
        try {
            jag.setX(speed);
            lastSpeedSetpoint = speed;
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setPosition(double position) {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return false;
        }

        try {
            if(controlMode != ControlMode.POSITION) {
                setMode(ControlMode.POSITION);
            }
        } catch(InvalidConfigurationException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        if(isReversed) {
            position = -position;
        }

        if(DEBUG) {
            System.out.println("[CANJaguar] Setting position to " + position);
        }
        try {
            jag.setX(position);
            lastPositionSetpoint = position;
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public void setMode(int mode) throws InvalidConfigurationException {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return;
        }

        if(DEBUG) {
            System.out.println("[CANJaguar - setMode] Changing mode from " + controlMode + " to " + mode);
        }
        try {
            switch(mode) {
                case ControlMode.PERCENT:
                    jag.changeControlMode(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kPercentVbus);
                    controlMode = ControlMode.PERCENT;
                    break;
                case ControlMode.POSITION:
                    if(!varsSetForPosition()) {
                        throw new InvalidConfigurationException("Missing variables to change to position mode");
                    }
                    jag.changeControlMode(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kPosition);
                    jag.setPositionReference(PositionReference.kQuadEncoder);
                    jag.configEncoderCodesPerRev(encoderCodesPerRevolution);
                    jag.setPID(positionP, positionI, positionD);
                    jag.enableControl();
                    controlMode = ControlMode.POSITION;
                    break;
                case ControlMode.SPEED:
                    if(DEBUG) {
                        System.out.println("[CANJaguar - setMode] Changing to speed mode");
                    }
                    if(!varsSetForSpeed()) {
                        throw new InvalidConfigurationException("Missing variables to change to speed mode");
                    }
                    jag.changeControlMode(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kSpeed);
                    jag.setSpeedReference(SpeedReference.kQuadEncoder);
                    jag.setPositionReference(PositionReference.kQuadEncoder);
                    jag.configEncoderCodesPerRev(encoderCodesPerRevolution);
                    if(DEBUG) {
                        System.out.println("[CANJaguar - setMode] P=" + speedP + " I=" + speedI + " D=" + speedD);
                    }
                    jag.setPID(speedP, speedI, speedD);
                    jag.enableControl();
                    controlMode = ControlMode.SPEED;
                    break;
                case ControlMode.DISABLED:
                    jag.changeControlMode(edu.wpi.first.wpilibj.CANJaguar.ControlMode.kVoltage);
                    jag.setX(0.0);
                    jag.disableControl();
                    controlMode = ControlMode.DISABLED;
                    break;

            }
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
        }
        if(DEBUG) {
            System.out.println("[CANJaguar - setMode] Mode is now: " + controlMode);
        }
    }

    public double getPosition() {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return 0.0;
        }
        try {
            if(isReversed) {
                return -jag.getPosition();
            } else {
                return jag.getPosition();
            }
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return 0;
        }
    }

    public void setEncoderCodesPerRevolution(int codes) {
        encoderCodesPerRevolution = codes;
    }

    public void setSpeedPID(double p, double i, double d) {
        speedP = p;
        speedI = i;
        speedD = d;
    }

    public void setPositionPID(double p, double i, double d) {
        positionP = p;
        positionI = i;
        positionD = d;
    }

    public double getPositionD() {
        return positionD;
    }

    public void setPositionD(double positionD) {
        this.positionD = positionD;
    }

    public double getPositionI() {
        return positionI;
    }

    public void setPositionI(double positionI) {
        this.positionI = positionI;
    }

    public double getPositionP() {
        return positionP;
    }

    public void setPositionP(double positionP) {
        this.positionP = positionP;
    }

    public double getSpeedD() {
        return speedD;
    }

    public void setSpeedD(double speedD) {
        this.speedD = speedD;
    }

    public double getSpeedI() {
        return speedI;
    }

    public void setSpeedI(double speedI) {
        this.speedI = speedI;
    }

    private boolean varsSetForSpeed() {
        if(encoderCodesPerRevolution < 1) {
            return false;
        }
        if(speedP == 0) {
            return false;
        }
        return true;
    }

    private boolean varsSetForPosition() {
        if(encoderCodesPerRevolution < 1) {
            return false;
        }
        if(positionP < 0 || positionI < 0 || positionD < 0) {
            return false;
        }
        return true;
    }

    public double getSpeed() {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return 0.0;
        }
        try {
            return jag.getSpeed();
        } catch(Exception e) {
            System.out.println("Exception in getSpeed!");
            System.out.println("Exception thrown from Jaguar ID = " + id);
            e.printStackTrace();
            return 0;
        }
    }

    public int getMode() {
        return controlMode;
    }

    public double getLastPositionSetpoint() {
        return lastPositionSetpoint;
    }

    public double getLastSpeedSetpoint() {
        return lastSpeedSetpoint;
    }

    public double getCurrent() {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return 0.0;
        }
        try {
            return jag.getOutputCurrent();
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return 0;
        }
    }

    public double getVoltage() {
        if(!jaguarSuccessfullyCreated) {
            System.out.println("Jaguar id " + id + " was not successfully created.");
            return 0.0;
        }
        try {
            return jag.getOutputVoltage();
        } catch(CANTimeoutException ex) {
            System.out.println("Exception thrown from Jaguar ID = " + id);
            ex.printStackTrace();
            return 0;
        }
    }
    
    public edu.wpi.first.wpilibj.CANJaguar getWpiJaguar(){
         return jag;
     }
}
