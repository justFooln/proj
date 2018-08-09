package com.kineticsnw.enigma.texthandling;

import java.io.IOException;

/**
 * @author jhake on 12/6/17.
 * Generic interface for i/o classes that specialize in specific sources:
 * e.g. files, URLs, databases, etc.
 */
public interface IoInterpreterIn {

  /** Should take care of opening the input source.
   * Can throw Exception to be handled by calling function.
   */
  public void openInput() throws IOException;

  /** Should take care of closing the source for input.
   * Can throw Exception to be handled by calling function.
   */
  public void closeInput() throws IOException;

  /** Indicates whether the input source is exhausted.
   * @return  TRUE if there is more input to be read
   * @return  FALSE if the input source cannot be read further
   */
  public Boolean isMoreInput() throws IOException;

  /** Get the next input - single character - from the input source
   * Can throw Exception to be handled by calling function.
   * @return  A String of length one, containing an alphabetic character. Punctuation, symbols, and numbers in the input are skipped.
   */
  public Character getNextInput() throws IOException;

}
