package org.naga.project.android.mikuroid.character;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

public abstract class MikuMessage {

  private static final int BATTERY_LOW_LEVL = 30;

  public static String generateBatteryMessage() {
    int batteryLevel = WidgetManager.getInstance().getCurrentBatteryLevel();

    String message = null;

    if (batteryLevel < MikuMessage.BATTERY_LOW_LEVL) {
      message = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_low);
    } else {
      message = WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_good);
    }

    return message;
  }

}
