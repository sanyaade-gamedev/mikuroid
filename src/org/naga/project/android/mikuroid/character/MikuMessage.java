package org.naga.project.android.mikuroid.character;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

public abstract class MikuMessage {

  private static final int BATTERY_LOW_LEVEL = 30;
  private static final int BATTERY_FULL_LEVEL = 100;

  public static String generateBatteryMessage() {
    int batteryLevel = WidgetManager.getInstance().getBatteryLevel();

    String message = null;

    if (batteryLevel < MikuMessage.BATTERY_LOW_LEVEL) {
      message = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_low);
    } else if (batteryLevel == MikuMessage.BATTERY_FULL_LEVEL) {
      message = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_full);
    } else {
      message = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_good);
    }

    return message;
  }

}
