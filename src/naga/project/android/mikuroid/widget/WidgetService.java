package naga.project.android.mikuroid.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetService extends Service {

    private static final String TAG = "WidgetUpdateService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(WidgetService.TAG, "onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(WidgetService.TAG, "onStart()");
        
        WidgetManager.getInstance().setContext(this);

        // Build the widget update
        RemoteViews updateViews = WidgetManager.getInstance().buildUpdate(this);
        Log.d(WidgetService.TAG, "update built");

        // Push update for this widget to the home screen
        ComponentName thiswiget = new ComponentName(this, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thiswiget, updateViews);
        Log.d(WidgetService.TAG, "widget updated");
        
        WidgetManager.getInstance().updateRemoteViews();
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
        
        // Destroy task manager.
        TaskManager.getInstance().Destroy();
    }

    @Override
    public void onLowMemory() {
        Log.d(WidgetService.TAG, "onLowMemory()");
    }

}
