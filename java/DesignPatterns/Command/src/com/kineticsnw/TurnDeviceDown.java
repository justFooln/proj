package com.kineticsnw;

public class TurnDeviceDown implements Command {

  private ElectronicDevice theDevice;

  public TurnDeviceDown(ElectronicDevice targetDevice){
    theDevice = targetDevice;
  }


  public void execute() {

    theDevice.volumeDown();

  }

  public void undo() {

    theDevice.volumeUp();

  }

}