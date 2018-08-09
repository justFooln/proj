package com.kineticsnw.enigma.application;

import com.kineticsnw.enigma.cryptoutilities.AlphaList;
import com.kineticsnw.enigma.engine.machine.EngineCONST;

public class Enigma {

    public static void main(String[] args) {

      AlphaList test1 = new AlphaList( RotorDef.ALPHABET.toString());
      String tempLame = "ABC";
      AlphaList test2 = new AlphaList( tempLame);
      String test4 = test1.getString();
      test4 = "I changed!";
      System.out.println( test1.getString() );
      System.out.println( test2.getString() );
      System.out.println( test4 );

    }
}
