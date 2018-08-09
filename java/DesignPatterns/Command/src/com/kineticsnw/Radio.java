package com.kineticsnw;


public class Radio extends ElectronicDeviceImpl {

  @Override
  public void on() {
    super.on();
    deviceSpeaker("Radio is on");
  }

  @Override
  public void off() {
    super.off();
    deviceSpeaker("Radio is off");
  }

  @Override
  public void volumeUp() {
    super.volumeUp();
    deviceSpeaker("Radio Volume is at: " + devVolume);
  }

  @Override
  public void volumeDown() {
    super.volumeDown();
    deviceSpeaker("Radio Volume is at: " + devVolume);
  }

}

