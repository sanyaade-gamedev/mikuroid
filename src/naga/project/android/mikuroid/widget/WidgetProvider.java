package naga.project.android.mikuroid.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Provide miku widget.
 * 
 * @author reciente
 * 
 */
public class WidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManger,
      int[] appWidgetIds) {
    Log.d("WidgetProvider", "onUpdate()");
    // Start WidgetService.
    context.startService(new Intent(context, WidgetService.class));
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
    Log.d("WidgetProvider", "onDeleted");
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Log.d("WidgetProvider", "onDisabled");
  }

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d("WidgetProvider", "onEnabled");
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    Log.d("WidgetProvider", "onReceive");

    String action = intent.getAction();

    // Stop service when discard widget from home.
    if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
      Log.d("WidgetProvider", "battery changed");
    } else if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
      Log.d("WidgetProvider", "stop service");
      context.stopService(new Intent(context, WidgetService.class));
    }
  }

}
