package com.kineticsnw;

public class AccountNumberCheck{

  private int accountNumber;

  public AccountNumberCheck(int newAcctNum){
    accountNumber = newAcctNum;
  }

  public int getAccountNumber() { return accountNumber; }

  public boolean accountActive(int acctNumToCheck){

    if(acctNumToCheck == getAccountNumber()) {

      return true;

    } else {

      return false;

    }

  }

}