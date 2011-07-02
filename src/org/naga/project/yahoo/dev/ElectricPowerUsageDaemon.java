package org.naga.project.yahoo.dev;

import java.util.Timer;
import java.util.TimerTask;

public class ElectricPowerUsageDaemon {

  public ElectricPowerUsageDaemon() {
    timer = new Timer(true);
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        ElectricPowerUsageService.updateElectricPowerUsage();
      }
    }, 500, 5000 * 60 * 30);
  }

  private Timer timer;

}
