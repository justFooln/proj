package com.kineticsnw;

public class SecurityCodeCheck {

  private int securityCode;

  public SecurityCodeCheck( int securityCode ) {
    this.securityCode = securityCode;
  }

  public int getSecurityCode() { return securityCode; }

  public boolean isCodeCorrect(int secCodeToCheck){

    if(secCodeToCheck == getSecurityCode()) {

      return true;

    } else {

      return false;

    }

  }

}