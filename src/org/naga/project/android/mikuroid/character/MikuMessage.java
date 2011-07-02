package org.naga.project.android.mikuroid.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.yahoo.dev.ElectricPowerUsage;
import org.naga.project.yahoo.dev.ElectricPowerUsageInformation;
import org.naga.project.yahoo.dev.ElectricPowerUsageService;

import android.content.res.Resources;
import android.os.BatteryManager;
import android.util.Log;

public abstract class MikuMessage {

  /**
   * Battery is low when battery level is lower than this number.
   */
  private static final int BATTERY_LOW_LEVEL = 50;

  /**
   * Battery is very low when battery level is lower than this number.
   */
  private static final int BATTERY_DANGER_LEVEL = 20;

  /**
   * Battery is deadly when battery level is lower than this number.
   */
  private static final int BATTERY_DEADLY_LEVEL = 5;

  /**
   * Battery is FULL when battery level is equal this number.
   */
  private static final int BATTERY_FULL_LEVEL = 100;

  /**
   * Generate messages about battery.
   * 
   * @return
   */
  public static List<String> generateBatteryLevelMessage() {
    List<String> messageList = new ArrayList<String>();

    Information info = WidgetManager.getInstance().information;

    String statusMsg = null;
    if (info.getBatteryStatus().intValue() == BatteryManager.BATTERY_STATUS_CHARGING) {
      statusMsg = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_charging);
    }

    int batteryLevel = info.getBatteryLevel().intValue();

    Resources res = WidgetManager.getInstance().getContext().getResources();
    String levelMsg = null;
    if (batteryLevel < MikuMessage.BATTERY_DEADLY_LEVEL) {
      levelMsg = String.format(res.getString(R.string.battery_deadly),
          batteryLevel);
    } else if (batteryLevel < MikuMessage.BATTERY_DANGER_LEVEL) {
      levelMsg = String.format(res.getString(R.string.battery_danger),
          batteryLevel);
    } else if (batteryLevel < MikuMessage.BATTERY_LOW_LEVEL) {
      levelMsg = String.format(res.getString(R.string.battery_low),
          batteryLevel);
    } else if (batteryLevel == MikuMessage.BATTERY_FULL_LEVEL) {
      levelMsg = String.format(res.getString(R.string.battery_full),
          batteryLevel);
    } else {
      levelMsg = String.format(res.getString(R.string.battery_good),
          batteryLevel);
    }

    if (null != statusMsg) {
      messageList.add(statusMsg + levelMsg);
    } else {
      messageList.add(levelMsg);
    }

    return messageList;
  }

  /**
   * Generate message about electric power usage.
   * 
   * @return
   */
  public static String generateElectricPowerUsageMessage() {
    List<String> messageList = new ArrayList<String>();

    ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;

    Resources res = WidgetManager.getInstance().getContext().getResources();

    if (info.useTokyo) {
      if (info.tokyo == null) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ElectricPowerUsageService.AREA,
            ElectricPowerUsageService.TOKYO);
        info.tokyo = ElectricPowerUsageService.httpRequest(params);
      }

      if (info.tokyo != null) {
        String messTokyo = String.format(res.getString(
            R.string.electric_power_usage_tokyo, (float) info.tokyo.usage
                / (float) info.tokyo.capacity * 100));
        messageList.add(messTokyo);
        Log.d("MIKU MESSAGE", messTokyo);
      }
    }

    if (info.useTohoku) {
      if (info.tohoku == null) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ElectricPowerUsageService.AREA,
            ElectricPowerUsageService.TOHOKU);
        info.tohoku = ElectricPowerUsageService.httpRequest(params);
      }

      if (info.tohoku != null) {
        String messTohoku = String.format(
            res.getString(R.string.electric_power_usage_tohoku),
            (float) info.tohoku.usage / (float) info.tohoku.capacity * 100);
        messageList.add(messTohoku);
        Log.d("MIKU MESSAGE", messTohoku);
      }
    }

    if (info.useKansai) {
      if (info.kansai == null) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ElectricPowerUsageService.AREA,
            ElectricPowerUsageService.KANSAI);
        info.kansai = ElectricPowerUsageService.httpRequest(params);
      }

      if (info.kansai != null) {
        String messKansai = String.format(
            res.getString(R.string.electric_power_usage_kansai),
            (float) info.kansai.usage / (float) info.kansai.capacity * 100);
        messageList.add(messKansai);
        Log.d("MIKU MESSAGE", messKansai);
      }
    }

    if (messageList.isEmpty()) {
      return res.getString(R.string.electric_power_usage_request_error);
    }

    String message = res.getString(R.string.electric_power_usage_begin);
    for (String mess : messageList) {
      message += "\n" + mess;
    }

    return message;
  }

}
