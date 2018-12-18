/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import java.util.HashMap;

import com.kineticsnw.enigma.machine.engine.EngineCONST;

/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
class RotorUtils {

  RotorUtils() {
  }

  class WiringTable {
    HashMap<Character, Character> deviceTable;
    WiringTable()  { }
  }

  // for indexing & reflector rotors
  static WiringTable makeWiringMap( EngineCONST.RotorDef rotor ) {
    String clearAlpha = EngineCONST.RotorDef.ALPHABET.wiringMap();
    String encryptAlpha = rotor.wiringMap();
    RotorUtils utility = new RotorUtils();
    WiringTable newMap = utility.makeNewWiringTable();
    newMap.deviceTable = new HashMap<>( clearAlpha.length() );
    utility.populateTable( newMap, clearAlpha, encryptAlpha );
    return newMap;
  }

  // for plugboards
  static WiringTable makeWiringMap( String clearChars, String mappedChars ) {
    String clearAlphaFull = EngineCONST.RotorDef.ALPHABET.wiringMap();
    RotorUtils utility = new RotorUtils();
    String encryptAlphaFull = utility.expandPlugboardMap( clearChars, mappedChars, clearAlphaFull );
    WiringTable newMap = utility.makeNewWiringTable();
    newMap.deviceTable = new HashMap<>( EngineCONST.RotorDef.ALPHABET.wiringMap().length() );
    utility.populateTable( newMap, clearAlphaFull, encryptAlphaFull );
    return newMap;
  }

  private WiringTable makeNewWiringTable() {
    WiringTable newMap = new WiringTable();
    return newMap;
  }

  private void populateTable( WiringTable table, String clearChars, String mappedChars ) {
    for ( char clearChar : clearChars.toCharArray() ) {
      for ( char encryptChar : mappedChars.toCharArray() ) {
        table.deviceTable.put( Character.valueOf( clearChar ), Character.valueOf( encryptChar ) );
      }
    }
  }

  private String expandPlugboardMap( String clearChars, String mappedChars, String clearAlphaFull ) {
    String encryptAlphaFull =  new String(clearAlphaFull);
    char[] mappedCharsArr = mappedChars.toCharArray();
    for (char mappedChar : mappedCharsArr) {
      int loc = mappedChars.indexOf( mappedChar );
      char charToRepl = clearChars.charAt( loc );
      encryptAlphaFull.replace( charToRepl, mappedChar );
    }
    return encryptAlphaFull;
  }
}
