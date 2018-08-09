package com.kineticsnw;

public class TurnDeviceOn implements Command {

  private ElectronicDevice theDevice;

  public TurnDeviceOn(ElectronicDevice targetDevice){
    theDevice = targetDevice;
  }

  public void execute() {

    theDevice.on();

  }

  public void undo() {

    theDevice.off();

  }
}