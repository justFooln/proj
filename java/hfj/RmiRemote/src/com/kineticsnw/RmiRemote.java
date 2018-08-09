package com.kineticsnw;

import java.rmi.*;
import java.rmi.server.*;

public class RmiRemote extends UnicastRemoteObject implements I_RmiRemote {

  public RmiRemote() throws RemoteException {
  }

  public static void main( String[] args ) {

    try {
      I_RmiRemote service = new RmiRemote();
      Naming.rebind( "ServerHello", service );
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }

  }

  public String sayHello() {
    return "Server says hello!";
  }
}
