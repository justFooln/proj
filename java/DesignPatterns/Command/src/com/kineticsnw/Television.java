package com.kineticsnw;


public class Television extends ElectronicDeviceImpl {

  @Override
  public void on() {
    super.on();
    deviceSpeaker("TV is on");
  }

  @Override
  public void off() {
    super.off();
    deviceSpeaker("TV is off");
  }

  @Override
  public void volumeUp() {
    super.volumeUp();
    deviceSpeaker("TV Volume is at: " + devVolume);
  }

  @Override
  public void volumeDown() {
    super.volumeDown();
    deviceSpeaker("TV Volume is at: " + devVolume);
  }

}

