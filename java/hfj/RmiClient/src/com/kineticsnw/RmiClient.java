package com.kineticsnw;

import java.rmi.*;

public class RmiClient {

  public static void main(String[] args) {
    try {
      I_RmiRemote service = (I_RmiRemote) Naming.lookup("rmi://127.0.0.1/ServerHello");
      String s = service.sayHello();
      System.out.println(s);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
