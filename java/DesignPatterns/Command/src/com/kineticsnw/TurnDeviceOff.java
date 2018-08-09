package com.kineticsnw;

public class TurnDeviceOff implements Command {

  private ElectronicDevice theDevice;

  public TurnDeviceOff(ElectronicDevice targetDevice){
    theDevice = targetDevice;
  }


  public void execute() {

    theDevice.off();

  }

  // Used if you want to allow for undo
  // Do the opposite of execute()

  public void undo() {

    theDevice.on();

  }

}