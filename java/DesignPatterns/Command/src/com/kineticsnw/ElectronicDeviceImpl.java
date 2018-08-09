package com.kineticsnw;

/**
 * Created by jhake on 11/4/17.
 */
public abstract class ElectronicDeviceImpl implements ElectronicDevice {

  // All devices are created with zero volume
  protected int devVolume = 0;

  // All devices are created in off state
  // There isn't a functional need for on/off state in this inplementation,
  // but if we were storing a device state persistently we'd need it.
  private boolean onState = false;

  // CmdMgr specific to this device
  CmdMgr cmdInvoker;

  public ElectronicDeviceImpl() {
    cmdInvoker = new CmdMgr();
  }

  protected void deviceSpeaker( String msg ) {
    System.out.println(msg);
  }

  public CmdMgr getCmdMgr() {
    return this.cmdInvoker;
  }

  public void on() {
    onState = true;
  }

  public void off() {
    onState = false;
  }

  public void volumeUp() {
    devVolume++;
  }

  public void volumeDown() {
    devVolume--;
  }


}


