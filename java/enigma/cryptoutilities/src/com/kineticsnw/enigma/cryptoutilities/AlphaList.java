/*
 * Copyright (c) 2018 Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */
package com.kineticsnw.enigma.cryptoutilities;

/**
 * Class responsibilities & contract
 * Implements...why?
 * Extends...why?
 *   @author    John Hake
 *   @since     2018
 */
public class AlphaList {

  private String charString;

  public AlphaList(String alphabet) {
    this.charString = alphabet;
  }

  /**
   *
   * @return    A copy of the String held by this instance
   */
  public String getString() {
    return  String.valueOf(this.charString);
  }
  
}
