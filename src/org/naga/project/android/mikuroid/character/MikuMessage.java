package org.naga.project.android.mikuroid.character;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.os.BatteryManager;

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

    String levelMsg = null;
    if (batteryLevel < MikuMessage.BATTERY_DEADLY_LEVEL) {
      levelMsg = String.format(WidgetManager.getInstance().getContext()
          .getResources().getString(R.string.battery_deadly), batteryLevel);
    } else if (batteryLevel < MikuMessage.BATTERY_DANGER_LEVEL) {
      levelMsg = String.format(WidgetManager.getInstance().getContext()
          .getResources().getString(R.string.battery_danger), batteryLevel);
    } else if (batteryLevel < MikuMessage.BATTERY_LOW_LEVEL) {
      levelMsg = String.format(WidgetManager.getInstance().getContext()
          .getResources().getString(R.string.battery_low), batteryLevel);
    } else if (batteryLevel == MikuMessage.BATTERY_FULL_LEVEL) {
      levelMsg = String.format(WidgetManager.getInstance().getContext()
          .getResources().getString(R.string.battery_full), batteryLevel);
    } else {
      levelMsg = String.format(WidgetManager.getInstance().getContext()
          .getResources().getString(R.string.battery_good), batteryLevel);
    }

    if (null != statusMsg) {
      messageList.add(statusMsg + levelMsg);
    } else {
      messageList.add(levelMsg);
    }

    return messageList;
  }

  public static List<String> generateClockMessage() {
    List<String> messageList = new ArrayList<String>();

    return messageList;
  }

}
