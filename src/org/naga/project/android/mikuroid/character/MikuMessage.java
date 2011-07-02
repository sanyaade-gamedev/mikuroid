package org.naga.project.android.mikuroid.character;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;
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
  public static List<String> generateElectricPowerUsageMessage() {
    List<String> messageList = new ArrayList<String>();

    ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;

    info.tokyo = ElectricPowerUsageService.httpRequest();

    Resources res = WidgetManager.getInstance().getContext().getResources();
    if (info.useTokyo && info.tokyo != null) {
      String message = String.format(res.getString(
          R.string.electric_power_usage_tokyo, (float) info.tokyo.usage
              / (float) info.tokyo.capacity * 100));
      messageList.add(message);
      Log.d("MIKU MESSAGE", message);
    }
    if (info.useTohoku && info.tohoku != null) {
      String message = String.format(
          res.getString(R.string.electric_power_usage_tohoku),
          (float) info.tohoku.usage / (float) info.tohoku.capacity * 100);
      messageList.add(message);
      Log.d("MIKU MESSAGE", message);
    }
    if (info.useKansai && info.kansai != null) {
      String message = String.format(
          res.getString(R.string.electric_power_usage_kansai),
          (float) info.kansai.usage / (float) info.kansai.capacity * 100);
      messageList.add(message);
      Log.d("MIKU MESSAGE", message);
    }

    return messageList;
  }

}
