package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

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
        context.startService(new Intent(context, UpdateService.class));
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
            context.stopService(new Intent(context, UpdateService.class));
        }
    }

    public static class UpdateService extends Service {

        @Override
        public void onStart(Intent intent, int startId) {
            Log.d("MikuroidWidget.UpdateService", "onStart()");

            // Build the widget update
            RemoteViews updateViews = this.buildUpdate(this);
            Log.d("MikuroidWidget.UpdateService", "update built");

            // Push update for this widget to the home screen
            ComponentName thiswiget = new ComponentName(this,
                    WidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thiswiget, updateViews);
            Log.d("MikuroidWidget.UpdateService", "widget updated");
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public RemoteViews buildUpdate(Context context) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget_normal);

            return views;
        }
    }

}
