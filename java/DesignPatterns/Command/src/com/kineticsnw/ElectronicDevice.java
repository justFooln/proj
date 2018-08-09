package com.kineticsnw;


public interface ElectronicDevice {

  public CmdMgr getCmdMgr();

  public void on();

  public void off();

  public void volumeUp();

  public void volumeDown();

}