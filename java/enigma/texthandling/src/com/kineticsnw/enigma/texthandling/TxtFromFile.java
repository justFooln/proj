package com.kineticsnw.enigma.texthandling;

import java.io.*;
import java.util.Scanner;

/**
 * Created by jhake on 12/7/17.
 */
public class TxtFromFile implements IoInterpreterIn {

  // The reader for input
  private BufferedReader inStream;

  public TxtFromFile(){
  }

  private String GetFileName(String prompt){

    Scanner scanner = new Scanner( System.in );
    System.out.print( prompt );
    String input = scanner.nextLine();
    return input;
  }

  /**
   * Should take care of opening the input source.
   * Can throw Exception to be handled by calling function.
   * Could stand more file-specific error checking.
   * #TODO - smarten up with auto path setting, see Path class & MIDI example
   */
  public void openInput() throws IOException  {

    String inFile = GetFileName( "Enter the file name for input: ");
    this.inStream = new BufferedReader(new FileReader( inFile ));
  }

  /**
   * Should take care of closing the source for input.
   * Can throw Exception to be handled by calling function.
   */
  public void closeInput() throws IOException {
    inStream.close();
  }

  /**
   * Indicates whether the input source is exhausted.
   *
   * @return FALSE if the input source cannot be read further
   */
  public Boolean isMoreInput() throws IOException {
    return inStream.ready();
  }

  /**
   * Get the next input - single character - from the input source
   * Can throw Exception to be handled by calling function.
   *
   * @return An alphabetic Character. Punctuation, symbols, and numbers in the input
   * are skipped.  If nothing can be read, ' ' is returned.
   */
  public Character getNextInput() throws IOException {
    char[] buff;
    buff = new char[1];                                 // Just need a length of 1 to read a single character
    int numCharsRead;                                   // Will be -1 at end-of-file
    Character readChar = ' ' ;                          // We'll return this Character
    boolean doneReading = false;
    do {
      // try to read a character
      numCharsRead = inStream.read( buff, 0, 1 );
      if ( numCharsRead == 1 ) {
        // successfully read a character
        if ( Character.isLetter( buff[0] ) ) {
          // successfully read a letter, so save it for return
          readChar = Character.valueOf( buff[0] );
          doneReading = true;
        }
      } else {
        // Could not read a character so we're done
        doneReading = true;
        readChar = ' ';
      }
    } while (!doneReading);

    return readChar;

  }

}
