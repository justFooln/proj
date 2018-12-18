package com.kineticsnw.enigma.texthandling;

import java.io.IOException;

/**
 * @author jhake on 12/6/17.
 * Generic interface for i/o classes that specialize in specific sources:
 * e.g. files, URLs, databases, etc.
 */
public interface IoInterpreterOut {

  /** Takes care of opening the output source for writing.
   * Can throw Exception to be handled by calling function.
   */
  public void openOutput() throws IOException ;

  /** Must take care of closing the source for output.
   * Delegates everything to the IoInterpreter.
   * Can throw Exception to be handled by calling function.
   */
  public void closeOutput() throws IOException;

  /** Writes a Character to the output
   * @param out a Character.
   * Can throw Exception to be handled by calling function.
   * #TODO  More efficient to use: write(String s, int off, int len), so use String as input parm
   */
  public void writeNextOutput( Character out ) throws IOException;



}
