/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.engine;

import com.kineticsnw.enigma.machine.rotors.Rotor;
import com.kineticsnw.enigma.machine.rotors.RotorBuilder;

/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class EncryptionEngine {

// Properties

// Methods

  public EncryptionEngine() {
    String plugboardClear = "ABCDEFGHIJ";
    String plugboardMap = "ABCDEFGHIJ";

    RotorSetUp rightRotorSpec = new RotorSetUp( EngineCONST.RotorDef.RTR_1, 'A', 'A' );
    RotorSetUp middleRotorSpec = new RotorSetUp( EngineCONST.RotorDef.RTR_2, 'A', 'A');
    RotorSetUp leftRotorSpec = new RotorSetUp( EngineCONST.RotorDef.RTR_3, 'A', 'A');
    RotorSetUp reflectorSpec = new RotorSetUp( EngineCONST.RotorDef.REFL_B, EngineCONST.NULL_RINGPOS, EngineCONST.NULL_STARTPOS);

    try {

      Rotor plugboard = RotorBuilder.createPlugboard( plugboardClear, plugboardMap );
      Rotor rightRotor = RotorBuilder.createIndexingRotor( rightRotorSpec );
      Rotor middleRotor = RotorBuilder.createIndexingRotor( middleRotorSpec );
      Rotor leftRotor = RotorBuilder.createIndexingRotor( leftRotorSpec );
      Rotor reflector = RotorBuilder.createReflector( reflectorSpec );

    } catch (ClassNotFoundException e) {
      System.out.println( e.getMessage() );
    }

  }

}
