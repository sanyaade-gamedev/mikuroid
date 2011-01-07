package org.naga.project.android;

public class Information {

  public Information() {
    super();

    this.batteryLevel = 0;
    this.batteryStatus = 0;
  }

  /**
   * Battery level.
   */
  private Integer batteryLevel;

  /**
   * Battery status. Charging etc.
   */
  private Integer batteryStatus;

  public Integer getBatteryLevel() {
    synchronized (batteryLevel) {
      return batteryLevel;
    }
  }

  public void setBatteryLevel(Integer level) {
    synchronized (batteryLevel) {
      batteryLevel = level;
    }
  }

  public Integer getBatteryStatus() {
    synchronized (batteryStatus) {
      return batteryStatus;
    }
  }

  public void setBatteryStatus(Integer status) {
    synchronized (batteryStatus) {
      batteryStatus = status;
    }
  }

}
