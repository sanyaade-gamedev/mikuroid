package org.naga.project.android;

public class Information {

  public Information() {
    super();

    this.batteryLevel = 0;
    this.powerConnected = false;
  }

  private Integer batteryLevel;

  public int getBatteryLevel() {
    synchronized (batteryLevel) {
      return batteryLevel;
    }
  }

  public void setBatteryLevel(Integer level) {
    synchronized (this.batteryLevel) {
      this.batteryLevel = level;
    }
  }

  private Boolean powerConnected;

  public Boolean getPowerConnected() {
    return powerConnected;
  }

  public void setPowerConnected(Boolean powerConnected) {
    this.powerConnected = powerConnected;
  }

}
