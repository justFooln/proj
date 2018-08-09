package com.kineticsnw.enigma.texthandling;

import java.io.*;
import java.util.Scanner;

/**
 * Created by jhake on 12/7/17.
 */
public class TxtToFile implements IoInterpreterOut {

  // The writer for output
  private BufferedWriter outStream;


  public TxtToFile(){
  }

  private String GetFileName(String prompt){

    Scanner scanner = new Scanner( System.in );
    System.out.print( prompt );
    String input = scanner.nextLine();
    return input;
  }

  /**
   * Takes care of opening the output source for writing.
   * Can throw Exception to be handled by calling function.
   * Could stand more file-specific error checking.
   * #TODO - smarten up with auto path setting, see Path class & MIDI example
   */
  public void openOutput() throws IOException {
    String outFile = GetFileName( "Enter the file name for output: ");
    this.outStream = new BufferedWriter( new FileWriter( outFile ) );
  }

  /**
   * Must take care of closing the source for output.
   * Can throw Exception to be handled by calling function.
   */
  public void closeOutput() throws IOException {
    this.outStream.close();         // Automatically flushes the buffer first
  }

  /**
   * Writes a Character to the output
   * @param out a Character.
   * Can throw Exception to be handled by calling function.
   */
  public void writeNextOutput( Character out ) throws IOException {
    // Convert to char
    // char outChar = out.charValue();
    // int writeVal = out.getNumericValue( outChar );
    // outStream.write( writeVal );
    // Counting on implicit cast of char to int.
    outStream.write( out.charValue() );
  }
}
