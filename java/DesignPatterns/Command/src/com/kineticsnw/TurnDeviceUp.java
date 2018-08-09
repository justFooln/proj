package com.kineticsnw;

public class TurnDeviceUp implements Command {

  private ElectronicDevice theDevice;

  public TurnDeviceUp(ElectronicDevice targetDevice){
    theDevice = targetDevice;
  }


  public void execute() {

    theDevice.volumeUp();

  }

  public void undo() {

    theDevice.volumeDown();

  }

}