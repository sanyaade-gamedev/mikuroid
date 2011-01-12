package org.naga.project.android.mikuroid.widget;

import org.naga.project.android.Information;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class WidgetService extends Service {

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d("WidgetUpdateService", "onCreate()");
    WidgetManager.getInstance().setContext(this);
    WidgetManager.getInstance().create();

    // Register receivers.
    this.registerReceiver(this.batteryReceiver, new IntentFilter(
        Intent.ACTION_BATTERY_CHANGED));
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    Log.d("WidgetUpdateService", "onStart()");

    if (null != intent && null != intent.getAction()) {
      Log.d("WidgetUpdateService", intent.getAction());
    }

    WidgetManager.getInstance().execute(intent);
  }

  @Override
  public IBinder onBind(Intent intent) {
    // There's no need to implement this currently.
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d("WidgetUpdateService", "onDestroy()");

    // Unregister receivers.
    this.unregisterReceiver(this.batteryReceiver);
  }

  @Override
  public void onLowMemory() {
    Log.d("WidgetUpdateService", "onLowMemory()");
  }

  private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d("WidgetService", "batteryReceiver");

      int status = intent.getIntExtra("status", 0);
      Integer batteryLevel = intent.getIntExtra("level", 0);

      Information info = WidgetManager.getInstance().information;

      info.setBatteryLevel(batteryLevel);
      Log.d("Power Level", Integer.toString(batteryLevel));
      info.setBatteryStatus(status);
    }

  };

}
