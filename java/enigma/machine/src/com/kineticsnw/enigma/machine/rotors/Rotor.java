/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

public interface Rotor {
  
  void resetRotor();
  
  Character getCurrentRotorPos();
  
  boolean atIndexPos();
  
  /*  Given an input on the right side of the rotor, it transforms to output on left side of rotor
  *   Each call advances the rotor wheel 1 character before performing the mapping
  */
  Character forwardTransform(Character rightInput);

  /*  Given an input on the left side of the rotor, it transform to output on the right side of the rotor
  *   Each call does not advance the rotor
  */
  Character inverseTransform(Character leftInput);
}

