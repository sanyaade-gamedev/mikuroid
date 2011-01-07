package org.naga.project.android.mikuroid.widget;

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
    this.registerReceiver(this.powerConnectReceiver, new IntentFilter(
        Intent.ACTION_POWER_CONNECTED));
    this.registerReceiver(this.powerDisconnectReceiver, new IntentFilter(
        Intent.ACTION_POWER_DISCONNECTED));
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    Log.d("WidgetUpdateService", "onStart()");

    WidgetManager.getInstance().execute();
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
    this.unregisterReceiver(this.powerConnectReceiver);
    this.unregisterReceiver(this.powerDisconnectReceiver);
  }

  @Override
  public void onLowMemory() {
    Log.d("WidgetUpdateService", "onLowMemory()");
  }

  private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d("WidgetService", "batteryReceiver");

      int batteryLevel = intent.getIntExtra("level", 0);
      WidgetManager.getInstance().getInformation()
          .setBatteryLevel(batteryLevel);
      Log.d("Power Level", Integer.toString(batteryLevel));
    }

  };

  private BroadcastReceiver powerConnectReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d("WidgetService", "powerConnectReceiver");
    }

  };

  private BroadcastReceiver powerDisconnectReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d("WidgetService", "powerDisconnectReceiver");
    }

  };

}
