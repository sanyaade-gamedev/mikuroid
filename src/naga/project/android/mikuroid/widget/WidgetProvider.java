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

    private static final String TAG = "WidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManger, int[] appWidgetIds) {
        Log.d(WidgetProvider.TAG, "onUpdate()");
        // Start WidgetService.
        context.startService(new Intent(context, WidgetService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(WidgetProvider.TAG, "onDeleted");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(WidgetProvider.TAG, "onDisabled");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(WidgetProvider.TAG, "onEnabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(WidgetProvider.TAG, "onReceive");

        // Stop service when discard widget from home.
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED
                .equals(intent.getAction())) {
            Log.d(WidgetProvider.TAG, "stop service");
            context.stopService(new Intent(context, WidgetService.class));
        }
    }

}
