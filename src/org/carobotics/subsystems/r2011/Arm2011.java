package org.carobotics.subsystems.r2011;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import org.carobotics.hardware.CANJaguar;
import org.carobotics.hardware.Solenoid;

/**
 * @author CaRobotics
 */
public class Arm2011 {
    CANJaguar topMotor;
    Victor lowerMotor;
    
    DigitalInput armUpper, armLower;
    
    Solenoid claw;
    
    public Arm2011(int topJagId, int lowerVictorId, int topLimInpt, int botLimInpt, int solenoidId){
        topMotor = new CANJaguar(topJagId);
        lowerMotor = new Victor(lowerVictorId);
        armUpper = new DigitalInput(topLimInpt);
        armLower = new DigitalInput(botLimInpt);
        
        System.out.println("Creating solenoid...");
        claw = new Solenoid(solenoidId);
        System.out.println("Done creating solenoid");
       
    }
    
    public CANJaguar getTopMotor(){
        return topMotor;
    }
    
    public Victor getLowerMotor(){
        return lowerMotor;
    }

    public DigitalInput getArmLower() {
        return armLower;
    }

    public DigitalInput getArmUpper() {
        return armUpper;
    }
    
    public Solenoid getClawSolenoid(){
        return claw;
    }
    
}
