package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetManager {

  /**
   * TAG for Log.
   */
  private static final String TAG = "WidgetManager";

  public static WidgetManager getInstance() {
    if (null == WidgetManager.instance) {
      WidgetManager.instance = new WidgetManager();
      WidgetManager.instance.miku = new MikuHatsune();
    }

    return WidgetManager.instance;
  }

  private WidgetManager() {
    super();
  }

  private static WidgetManager instance;

  /**
   * Set Context. If context is not seted, set context and pending intent.
   * 
   * @param ct
   */
  public synchronized void setContext(Context ct) {
    if (null != this.context) {
      return;
    }

    this.context = ct;

    // Create an Intent to launch Activity
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_MEDIA_BUTTON);
    intent.setType("text/plain");
    this.pendingIntent = PendingIntent.getService(this.context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    this.widget = new ComponentName(this.context, WidgetProvider.class);
    this.appWidgetManager = AppWidgetManager.getInstance(this.context);
  }

  public RemoteViews buildUpdate(Context context) {
    RemoteViews views = new RemoteViews(context.getPackageName(),
        R.layout.widget_message);

    return views;
  }

  public void updateRemoteViews() {
    Log.d(WidgetManager.TAG, "updateRemoteViews");
    if (null == this.context) {
      return;
    }

    RemoteViews views = new RemoteViews(this.context.getPackageName(),
        R.layout.widget_message);

    this.miku.updateRemoteViews(views);

    // Set pending intent to check has miku clicked.
    views.setOnClickPendingIntent(R.id.miku, this.pendingIntent);

    this.appWidgetManager.updateAppWidget(this.widget, views);
  }

  /** Context of WidgetProvider. */
  private Context context;

  private PendingIntent pendingIntent;
  private ComponentName widget;

  /** AppWidgetManager to manage this widget. */
  private AppWidgetManager appWidgetManager;

  private MikuHatsune miku;

}
