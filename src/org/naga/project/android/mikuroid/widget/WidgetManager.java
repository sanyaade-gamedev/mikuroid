package org.naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.naga.project.android.framework.scene.TalkScene;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetObject.WidgetMode;
import org.naga.project.nicovideo.NicovideoEntry;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetManager implements WidgetUpdate, WidgetView {

  /**
   * Return singleton instance.
   * 
   * @return Singleton instance.
   */
  public static WidgetManager getInstance() {
    if (null == WidgetManager.instance) {
      WidgetManager.instance = new WidgetManager();
    }

    return WidgetManager.instance;
  }

  /**
   * Constructor.
   */
  private WidgetManager() {
    super();
  }

  public boolean create() {
    // create will call any times.
    // so check null to not overload object.
    if (null == WidgetManager.instance.miku) {
      WidgetManager.instance.miku = new MikuHatsune();
      WidgetManager.instance.miku.create();
    }

    if (null == WidgetManager.instance.nicoEntryQueue) {
      WidgetManager.instance.nicoEntryQueue = new ConcurrentLinkedQueue<NicovideoEntry>();
    }
    if (null == WidgetManager.instance.images) {
      WidgetManager.instance.images = new ConcurrentHashMap<Integer, Bitmap>();
    }

    // Set default mode.
    this.mode = WidgetMode.TALK;

    return true;
  }

  /**
   * Singleton instance.
   */
  private static WidgetManager instance;

  /**
   * Set Context. If context is not seted, set context and pending intent.
   * 
   * @param ct
   *          Android context.
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

  public Context getContext() {
    return context;
  }

  public boolean onUpdate() {
    Log.d("WidgetManager", "onUpdate()");

    this.miku.update();
    return true;
  }

  public boolean onView() {
    Log.d("WidgetManager", "onView()");
    RemoteViews views = new RemoteViews(this.context.getPackageName(),
        R.layout.widget_message);

    this.miku.view(views);

    // Set pending intent to check has miku clicked.
    views.setOnClickPendingIntent(R.id.miku, this.pendingIntent);

    this.appWidgetManager.updateAppWidget(this.widget, views);

    return true;
  }

  public void execute() {
    if (!this.onUpdate()) {
      // Skip view when result is false.
      return;
    }
    this.onView();
  }

  /**
   * Context of WidgetProvider.
   */
  private Context context;

  /**
   * Pending intent to set intent action.
   */
  private PendingIntent pendingIntent;

  /**
   * Widget component name.
   */
  private ComponentName widget;

  /**
   * AppWidgetManager to manage this widget.
   */
  private AppWidgetManager appWidgetManager;

  /**
   * Widget character Miku Hatsune.
   */
  private MikuHatsune miku;

  /**
   * Thread-safe nicovideo entry store.
   */
  private ConcurrentLinkedQueue<NicovideoEntry> nicoEntryQueue;

  /**
   * Thread-safe image store.
   */
  private ConcurrentHashMap<Integer, Bitmap> images;

  /**
   * Current battery level.
   */
  private Integer currentBatteryLevel = 0;

  public ConcurrentLinkedQueue<NicovideoEntry> getNicoEntryQueue() {
    return nicoEntryQueue;
  }

  public ConcurrentHashMap<Integer, Bitmap> getImages() {
    return images;
  }

  private WidgetObject.WidgetMode mode;

  private TalkScene talkScene;

  public int getCurrentBatteryLevel() {
    synchronized (currentBatteryLevel) {
      return currentBatteryLevel;
    }
  }

  public void setCurrentBatteryLevel(int currentBatteryLevel) {
    synchronized (this.currentBatteryLevel) {
      this.currentBatteryLevel = currentBatteryLevel;
    }
  }

}
