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
        Log.d("MikuroidWidget.MikuroidWidgetProvider", "onUpdate()");
        context.startService(new Intent(context, WidgetUpdateService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d("MikuroidWidget.MikuroidWidgetProvider", "onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d("MikuroidWidget.MikuroidWidgetProvider", "onDisabled");
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("MikuroidWidget.MikuroidWidgetProvider", "onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
     // Stop service when discard widget from home.
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(intent.getAction())) {
            context.stopService(new Intent(context, WidgetUpdateService.class));
        }
    }

}
