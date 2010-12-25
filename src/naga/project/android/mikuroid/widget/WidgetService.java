package naga.project.android.mikuroid.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WidgetService extends Service {

    private static final String TAG = "WidgetUpdateService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(WidgetService.TAG, "onCreate()");
        WidgetManager.getInstance().setContext(this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(WidgetService.TAG, "onStart()");

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
