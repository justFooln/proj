
/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.engine;


/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class RotorSetUp {

// Properties
  private final EngineCONST.RotorDef rotorID;
  private final Character ringSettingPosition;
  private final Character rotorStartPosition;

  public RotorSetUp( EngineCONST.RotorDef rotorID, Character ringSettingLetter, Character rotorStartLetter ) {
    this.rotorID = rotorID;
    this.ringSettingPosition = ringSettingLetter;
    this.rotorStartPosition = rotorStartLetter;
  }
  
  public EngineCONST.RotorDef getRotorID() {
    return this.rotorID;
  }
  
  public Character getRingSettingPosition() {
    return this.ringSettingPosition;
  }
  
  public Character getRotorStartPosition() {
    return this.rotorStartPosition;
  }

}
