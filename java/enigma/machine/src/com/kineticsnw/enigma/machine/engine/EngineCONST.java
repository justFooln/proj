
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
public final class EngineCONST {

  // Don't allow instantiation to prevent this from being subclassed.
  private EngineCONST() {
  }

  public static final Integer MAX_PLUGBOARD_CONN = Integer.valueOf( 10 );
  public static final char NULL_INDEX = '*';
  public static final String NULL_WIRE_MAP = "*";
  public static final Character NULL_RINGPOS = 'A';
  public static final Character NULL_STARTPOS = 'A';

  public enum RotorType {
    INDEXING,
    STATIC,
    PLUGBOARD
  }

  public enum RotorDef {
    // Definitions from http://users.telenet.be/d.rijmenants/en/enigmatech.htm#wiringtables
    // Indexing rotors - note the char must be contained in RotorDef.ALPHABET
    RTR_1 ( "EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q' ),
    RTR_2 ( "AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E' ),
    RTR_3 ( "BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V' ),
    // Reflector rotors - note there is no indexing letter
    ALPHABET ( "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ),
    REFL_B ( "YRUHQSLDPXNGOKMIEBFZCWVJAT" ),
    REFL_C ( "FVPJIAOYEDRZXWGCTKUQSBNMHL" ),
    // Plugboard doesn't have a map or indexing letter.
    PLUG_BD ( );

    private final RotorType myType;     // Indexing or static (non-rotary) ?
    private final String wiringMap;     // Substitution cypher 1:1 order with RotorDef.ALPHABET
    private final char indexAt;         // Indexing rotor position to trigger index of next (left) rotor

    // For indexing rotors
    RotorDef( String alphaWiring, char indexLetter ) {
      this.myType = RotorType.INDEXING;
      this.wiringMap = alphaWiring.toUpperCase();  // Just in case, force to upper case
      // for indexing rotors this character MUST match a character in RotorDef.ALPHABET
      this.indexAt = indexLetter;
    }

    // for stators
    RotorDef( String alphaWiring ) {
      this.myType = RotorType.STATIC;
      this.wiringMap = alphaWiring.toUpperCase();   // Just in case, force to upper case
      this.indexAt = NULL_INDEX;
    }

    // for plugboards
    RotorDef( ) {
      this.myType = RotorType.PLUGBOARD;
      this.wiringMap = NULL_WIRE_MAP;
      this.indexAt = NULL_INDEX;
    }

    public RotorType getMyType() {return this.myType;}
    public String wiringMap() { return this.wiringMap; }
    public char indexAt() { return this.indexAt; }
  }


}
