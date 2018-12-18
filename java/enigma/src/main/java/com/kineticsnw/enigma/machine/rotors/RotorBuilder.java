/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import com.kineticsnw.enigma.machine.engine.EngineCONST;
import com.kineticsnw.enigma.machine.engine.RotorSetUp;

public class RotorBuilder {

  public static Rotor createIndexingRotor(RotorSetUp rotorConfig) throws ClassNotFoundException {
    if (  !( rotorConfig.getRotorID().equals( EngineCONST.RotorType.INDEXING ) ) ) {
      throw new ClassNotFoundException("RotorType " + rotorConfig.getRotorID().toString() + "incompatible with Class " + IndexingRotor.class.toString());
    } else {
      return new IndexingRotor( rotorConfig );
    }
  }

  public static Rotor createReflector(RotorSetUp rotorConfig) throws ClassNotFoundException {
    if (  !( rotorConfig.getRotorID().equals( EngineCONST.RotorType.STATIC ) ) ) {
      throw new ClassNotFoundException("RotorType " + rotorConfig.getRotorID().toString() + "incompatible with Class " + Reflector.class.toString());
    } else {
      return new Reflector( rotorConfig );
    }
  }

  public static Rotor createPlugboard( String clearChars, String mappedChars ) throws ClassNotFoundException {
    boolean inputsBad = ( clearChars.length() != mappedChars.length() );
    inputsBad =  inputsBad && ( clearChars.isEmpty() || (clearChars.length() > EngineCONST.MAX_PLUGBOARD_CONN) );
    inputsBad = inputsBad && ( mappedChars.isEmpty() || (mappedChars.length() > EngineCONST.MAX_PLUGBOARD_CONN) );
    if ( inputsBad ) {
      throw new ClassNotFoundException( "Incompatible clear and mapped String inputs incompatible with " + IndexingRotor.class.toString() );
    } else {
      return new PlugBoard( clearChars, mappedChars );
    }
  }

}