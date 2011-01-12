package org.naga.project.android.mikuroid.character;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

public abstract class MikuMessage {

  private static final int BATTERY_LOW_LEVEL = 30;
  private static final int BATTERY_FULL_LEVEL = 100;

  public static List<String> generateBatteryLevelMessage() {
    Integer batteryLevel = WidgetManager.getInstance().information
        .getBatteryLevel();

    List<String> messageList = new ArrayList<String>();

    if (batteryLevel.intValue() < MikuMessage.BATTERY_LOW_LEVEL) {
      messageList.add(WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_low));
    } else if (batteryLevel.intValue() == MikuMessage.BATTERY_FULL_LEVEL) {
      messageList.add(WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_full));
    } else {
      messageList.add(WidgetManager.getInstance().getContext().getResources()
          .getString(R.string.battery_good));
    }

    return messageList;
  }

}
