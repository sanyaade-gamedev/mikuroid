package naga.project.android.mikuroid.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class TaskManager {

    public static TaskManager getInstance() {
        if (null == TaskManager.instance) {
            TaskManager.instance = new TaskManager();
        }

        return TaskManager.instance;
    }

    public synchronized void setContext(Context ct) {
        if (null == this.context) {
            this.context = ct;

            // Create an Intent to launch Activity
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_BUTTON);
            intent.setType("test/plain");
            this.pendingIntent = PendingIntent.getService(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            this.widget = new ComponentName(this.context, WidgetProvider.class);
            this.appWidgetManager = AppWidgetManager.getInstance(context);
        }
    }

    private static TaskManager instance;

    private Context context;

    private PendingIntent pendingIntent;
    private ComponentName widget;
    private AppWidgetManager appWidgetManager;

}
