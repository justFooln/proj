package com.kineticsnw.enigma.texthandling;

import java.io.IOException;
/**
 * @author jhake on 12/6/17.
 * Converts input sources to plaintext suitable for encryption
 * e.g. "Now is the time." becomes "NOWIS THETIM E"
 * Most of the work is delegated to an interface that allows specialization
 * of input/output sources.
 */
public abstract class ConvToClearText {

  // Interface to handle all lower level input operations
  protected IoInterpreterIn ioSrc;

  // Interface to handle all lower level output operations
  protected IoInterpreterOut ioDest;

  // Number of output characters before inserting a space in the output stream
  protected static final int LETTER_GROUP_SIZE = 5;

  /**
  * Constructor is intended to be specialized by subclasses
  */
  public ConvToClearText() {
  }

  /**
   * This is the default method to do the conversion from input to output.
   * It's relatively inefficient because it generally can't take advantage
   * of efficiencies specific to particular sources - e.g. iterators for file buffers.
   * Subclasses can override, but as more specialized sources are added this code
   * may get stale.
   * Overrides in subclasses must handle exceptions thrown in called methods.
   * #TODO - small optimization: write out 5+1 length strings instead of a character at a time.
   * #TODO - better optimization: write out 12 groups of 5+1 strings at a time.
   */
  public void ProcessContent() {
    try {
      openInput();
      openOutput();
      Character inputChar = ' ';
      Character outputChar = ' ';
      int numLettersWritten = 0;

      // As long as there is input...
      while ( isMoreInput() ) {
        // Read the input
        inputChar = getNextInput();
        // Write letters to the output stream
        if (Character.isLetter( inputChar )) {
          outputChar = transformInput( inputChar );       // Transform letters to upper case
          writeNextOutput( outputChar );                  // Write to output
          numLettersWritten += 1;
          if (numLettersWritten == LETTER_GROUP_SIZE) {
            writeNextOutput( ' ' );  // Write a space between letter groups
            numLettersWritten = 0;
          }
        }
      }
      // These actually self-close on exception, so no need to handle in catch
      closeInput();
      closeOutput();
    } catch ( IOException e ) {
      System.out.println("IO Exception in ProcessContent: " + e.getMessage());
    } catch ( Exception e ) {
      System.out.println("Exception in ProcessContent: " + e.getMessage());
    }
  }

  /** Takes care of opening the input source for input.
   * Delegates everything to the IoInterpreter specific to the source.
   * Throws Exception
   */
  public void openInput() throws IOException { ioSrc.openInput(); }

  /** Takes care of closing the source for input.
   * Delegates everything to the IoInterpreter.
   * Throws Exception
   */
  public void closeInput() throws IOException { ioSrc.closeInput(); }

  /** Indicates whether the input source is exhausted.
   * @return  TRUE if there is more input to be read
   * @return  FALSE if the input source cannot be read further
   */
  public Boolean isMoreInput() throws IOException { return ioSrc.isMoreInput(); }

  /** Gets the next input - single character - from the input source
   * Delegates everything to the IoInterpreter
   * @return  A String of length one, containing an alphabetic character. Punctuation, symbols, and numbers in the input are skipped.
   */
  public Character getNextInput() throws IOException { return ioSrc.getNextInput(); }

  /** Transforms one chunk - single character - from the input source so the chunk is suitable for output
   *  @param  oneChar   an alphabetic Character.
   *  @return an upper case alphabetic character
   */
  public Character transformInput(Character oneChar) {
    return Character.toUpperCase( oneChar );
  }

  /** Takes care of opening the output source for writing.
   * Delegates everything to the IoInterpreter specific to the destination.
   * Throws Exception
   */
  public void openOutput() throws IOException { ioDest.openOutput(); }

  /** Takes care of closing the source for output.
   * Delegates everything to the IoInterpreter.
   * Throws Exception
   */
  public void closeOutput() throws IOException {
    ioDest.closeOutput();
  }

  /** Writes a chunk - a single character - to the output
   * Delegates everything to the IoInterpreter.
   * @param out a Character.
   * Throws Exception
   */
  public void writeNextOutput(Character out) throws IOException {
    ioDest.writeNextOutput( out );
  }


}


