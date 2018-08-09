package com.kineticsnw;

import java.rmi.*;

/**
 * Created by jhake on 5/31/17.
 */
public interface I_RmiRemote extends Remote {

  public String sayHello() throws RemoteException;

}
