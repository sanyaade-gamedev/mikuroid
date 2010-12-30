package naga.project.android.mikuroid.widget;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class WidgetService extends Service {

  private static final String TAG = "WidgetUpdateService";

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(WidgetService.TAG, "onCreate()");
    WidgetManager.getInstance().setContext(this);

    // Register battery receiver.
    this.registerReceiver(this.batteryReceiver, new IntentFilter(
        Intent.ACTION_BATTERY_CHANGED));
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    Log.d(WidgetService.TAG, "onStart()");

    WidgetManager.getInstance().update();
    WidgetManager.getInstance().view();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // There's no need to implement this currently.
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(WidgetService.TAG, "onDestroy()");

    // Unregister battery receiver.
    this.unregisterReceiver(this.batteryReceiver);

    // Destroy task manager.
    TaskManager.getInstance().Destroy();
  }

  @Override
  public void onLowMemory() {
    Log.d(WidgetService.TAG, "onLowMemory()");
  }

  private int currentBatteryLevel = 0;

  public int getCurrentBatteryLevel() {
    return currentBatteryLevel;
  }

  private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d("WidgetService$batteryReceiver", "onReceive");
      currentBatteryLevel = intent.getIntExtra("level", 0);
      Log.d("Power Level", Integer.toString(currentBatteryLevel));
    }
  };

}
