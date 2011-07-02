package org.naga.project.yahoo.dev;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.naga.project.android.mikuroid.widget.WidgetManager;

public class ElectricPowerUsageDaemon {

  public ElectricPowerUsageDaemon() {
    timer = new Timer(true);
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;

        if (info.useTokyo) {
          HashMap<String, String> params = new HashMap<String, String>();
          params.put(ElectricPowerUsageService.AREA, ElectricPowerUsageService.TOKYO);
          info.tokyo = ElectricPowerUsageService.httpRequest(params);
        }
        if (info.useTohoku) {
          HashMap<String, String> params = new HashMap<String, String>();
          params.put(ElectricPowerUsageService.AREA, ElectricPowerUsageService.TOHOKU);
          info.tohoku = ElectricPowerUsageService.httpRequest(params);
        }
        if (info.useKansai) {
          HashMap<String, String> params = new HashMap<String, String>();
          params.put(ElectricPowerUsageService.AREA, ElectricPowerUsageService.KANSAI);
          info.kansai = ElectricPowerUsageService.httpRequest(params);
        }
      }
    }, 500, 5000 * 60 * 30);
  }

  private Timer timer;

}
