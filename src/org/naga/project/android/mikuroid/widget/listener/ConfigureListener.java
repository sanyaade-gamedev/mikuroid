package org.naga.project.android.mikuroid.widget.listener;

import org.naga.project.android.mikuroid.MikuroidConfigureActivity;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.Intent;

public class ConfigureListener implements Listener {

  public boolean onListen(Intent intent) {
    if (MikuroidIntent.ACTION_CONFIGURE.equals(intent.getAction())) {
      // Start configure activity.
      Intent activityIntent = new Intent(WidgetManager.getInstance()
          .getContext().getApplicationContext(),
          MikuroidConfigureActivity.class);
      activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      WidgetManager.getInstance().getContext().startActivity(activityIntent);
      
      return false;
    }

    return true;
  }

}
