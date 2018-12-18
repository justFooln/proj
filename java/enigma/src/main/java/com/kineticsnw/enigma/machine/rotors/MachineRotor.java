/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import com.kineticsnw.enigma.machine.engine.EngineCONST;
import com.kineticsnw.enigma.machine.engine.RotorSetUp;



/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class MachineRotor implements Rotor {


  // These properties are physical attributes of a rotor
  private EngineCONST.RotorType myType;
  private Character indexLetter;
  private WiringTable wiringMap;

  // These properties are part of the initial settings for the rotor. -
  private Character startRotorPos;
  private Character ringPos;

  // These properties are states that change during operation.
  private Character currentRotorPos;

  // TODO: Why is this needed?
  public MachineRotor() {}

  // Constructor for static and indexing rotors
  // For indexing rotors
  public MachineRotor( RotorSetUp rotorConfig ) {
    this.myType = rotorConfig.getRotorID().getMyType();
    this.indexLetter = rotorConfig.getRotorID().indexAt();
    this.startRotorPos = rotorConfig.getRotorStartPosition();
    this.ringPos = rotorConfig.getRingSettingPosition();
    this.setCurrentRotorPos( this.startRotorPos );
    this.wiringMap = RotorUtils.makeWiringMap( rotorConfig.getRotorID() );
  }
  
  public void resetRotor() {
    this.setCurrentRotorPos( this.startRotorPos );
  }
  
  public Character getCurrentRotorPos() { return this.currentRotorPos; }
  public void setCurrentRotorPos( Character newRotorPos ) { this.currentRotorPos = newRotorPos; }
  
  public boolean atIndexPos() {
    return ( this.myType == EngineCONST.RotorType.INDEXING ) && ( this.currentRotorPos == this.indexLetter );
  }
  
  /*  Given an input on the right side of the rotor, it transforms to output on left side of rotor
   *   Each call advances the rotor wheel 1 character before performing the mapping
   */
  public Character forwardTransform( Character rightInput ) {
    return null;
  }
  
  /*  Given an input on the left side of the rotor, it transform to output on the right side of the rotor
   *   Each call does not advance the rotor
   */
  public Character inverseTransform( Character leftInput ) {
    return null;
  }



  protected void setMyType( EngineCONST.RotorType myType ) {
    this.myType = myType;
  }

  protected void setIndexLetter( Character indexLetter ) {
    this.indexLetter = indexLetter;
  }

  protected void setStartRotorPos( Character startRotorPos ) {
    this.startRotorPos = startRotorPos;
  }
  public Character getStartRotorPos() {
    return this.startRotorPos;
  }

  protected void setRingPos( Character ringPos ) {
    this.ringPos = ringPos;
  }

  protected void setWiringMap( WiringTable wiringMap ) {
    this.wiringMap = wiringMap;
  }


}
