/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carobotics.subsystems;

import org.carobotics.hardware.Talon;

/**
 *
 * @author Student
 */
public class Thwacker {
    protected Talon motor;
    public Thwacker(int motor){
        this.motor = new Talon(motor);
    }

public Talon getMotor() {
        return motor;
    }
}