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

public class MikuroidWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManger, int[] appWidgetIds) {
        Log.d("MikuroidWidget.updateService", "onUpdate()");
        context.startService(new Intent(context, UpdateService.class));
    }
    
    public static class UpdateService extends Service {
        
        @Override
        public void onStart(Intent intent, int startId) {
            Log.d("MikuroidWidget.UpdateService", "onStart()");
            
            // Build the widget update
            RemoteViews updateViews = this.buildUpdate(this);
            Log.d("MikuroidWidget.UpdateService", "update built");
            
            // Push update for this widget to the home screen
            ComponentName thiswiget = new ComponentName(this, MikuroidWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thiswiget, updateViews);
            Log.d("MikuroidWidget.UpdateService", "widget updated");
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
        
        public RemoteViews buildUpdate(Context context) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_message);
            
            return views;
        }
    }

}
