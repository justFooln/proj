/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import com.kineticsnw.enigma.machine.engine.EngineCONST;

/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class PlugBoard extends MachineRotor {

// Properties

// Methods

  public PlugBoard() { }

  // Constructor for plugboard - treated as a special type of static rotor
    public PlugBoard( String clearChars, String mappedChars ) {
    super.setMyType( EngineCONST.RotorType.PLUGBOARD );
    super.setIndexLetter( EngineCONST.NULL_INDEX );
    super.setStartRotorPos( EngineCONST.NULL_STARTPOS );
    super.setRingPos( EngineCONST.NULL_RINGPOS);
    super.setCurrentRotorPos( EngineCONST.NULL_STARTPOS );
    super.setWiringMap( RotorUtils.makeWiringMap( clearChars, mappedChars ) );
  }

  @Override
  public void resetRotor() {
    super.resetRotor();
  }

  @Override
  public Character getCurrentRotorPos() {
    return super.getStartRotorPos();
  }

  @Override
  public boolean atIndexPos() {
    return false;
  }

  @Override
  public Character forwardTransform( Character rightInput ) {
    return super.forwardTransform( rightInput );
  }

  @Override
  public Character inverseTransform( Character leftInput ) {
    return super.inverseTransform( leftInput );
  }
}
