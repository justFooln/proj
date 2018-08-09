package com.kineticsnw.enigma.texthandling;

/**
 * @author jhake on 12/7/17.
 * Concrete implementation of ToPlaintext. Specialized for file input/output.
 */
public class PlainToClearTxtFile extends ConvToClearText {
  /**
   * Constructor is intended to be specialized by subclasses
   * Not much to do here but assign file handling implementation to interface.
   */
  public PlainToClearTxtFile() {
    super();
    this.ioSrc = new TxtFromFile();
    this.ioDest = new TxtToFile();
  }

}
