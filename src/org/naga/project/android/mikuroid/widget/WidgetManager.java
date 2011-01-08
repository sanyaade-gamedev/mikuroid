package org.naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.Mikuroid;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
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

  public static final int WIDGET_MODE_NONE = 0;
  public static final int WIDGET_MODE_WAIT = 1;
  public static final int WIDGET_MODE_TALK = 2;
  public static final int WIDGET_MODE_NICOVIDEO_RANKING = 3;

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
   * Singleton instance.
   */
  private static WidgetManager instance;

  /**
   * Constructor.
   */
  private WidgetManager() {
    super();

    this.nicoEntryQueue = new ConcurrentLinkedQueue<NicovideoEntry>();
    this.images = new ConcurrentHashMap<Integer, Bitmap>();
    this.information = new Information();
  }

  public boolean create() {
    // create will call any times.
    // so check null to not overload object.
    if (null == this.miku) {
      this.miku = new MikuHatsune();
      this.miku.create();
    }

    // Set default mode.
    this.mode = WidgetManager.WIDGET_MODE_TALK;

    return true;
  }

  /**
   * Set Context. If context is not exist, set context and pending intent.
   * 
   * @param ct
   *          Android context.
   */
  public synchronized void setContext(Context ct) {
    // Context is already exists.
    if (null != this.context) {
      return;
    }

    this.context = ct;

    // Create an Intent to get touch event.
    Intent intentMiku = new Intent();
    intentMiku.setAction(Mikuroid.ACTION_MIKU_TOUCH);
    this.pendingIntentMiku = PendingIntent.getService(this.context, 0,
        intentMiku, PendingIntent.FLAG_UPDATE_CURRENT);

    Intent intentYes = new Intent();
    intentYes.setAction(Mikuroid.ACTION_YES);
    this.pendingIntentYes = PendingIntent.getService(this.context, 0,
        intentYes, PendingIntent.FLAG_UPDATE_CURRENT);

    this.widget = new ComponentName(this.context, WidgetProvider.class);
    this.appWidgetManager = AppWidgetManager.getInstance(this.context);
  }

  public Context getContext() {
    return context;
  }

  public boolean onUpdate(Intent intent) {
    Log.d("WidgetManager", "onUpdate()");

    switch (this.mode.intValue()) {
    case WidgetManager.WIDGET_MODE_WAIT:
      this.miku.waitUpdate();
      break;

    case WidgetManager.WIDGET_MODE_TALK:
      this.miku.talkUpdate();
      break;

    case WidgetManager.WIDGET_MODE_NICOVIDEO_RANKING:
      break;

    case WidgetManager.WIDGET_MODE_NONE:
      break;
    }

    return true;
  }

  public boolean onView(Intent intent) {
    Log.d("WidgetManager", "onView()");
    RemoteViews views = new RemoteViews(this.context.getPackageName(),
        R.layout.widget_miku);

    switch (this.mode.intValue()) {
    case WidgetManager.WIDGET_MODE_WAIT:
      this.miku.waitView(views);
      break;

    case WidgetManager.WIDGET_MODE_TALK:
      this.miku.talkView(views);
      break;

    case WidgetManager.WIDGET_MODE_NICOVIDEO_RANKING:
      break;

    case WidgetManager.WIDGET_MODE_NONE:
      break;
    }

    // Set pending intent to check has miku clicked.
    views.setOnClickPendingIntent(R.id.miku, this.pendingIntentMiku);
    views.setOnClickPendingIntent(R.id.yes, this.pendingIntentYes);

    this.appWidgetManager.updateAppWidget(this.widget, views);

    return true;
  }

  public void execute(Intent intent) {
    if (!this.onUpdate(intent)) {
      // Skip view when result is false.
      return;
    }
    this.onView(intent);
  }

  /**
   * Context of WidgetProvider.
   */
  private Context context;

  /**
   * Pending intent to set intent action.
   */
  private PendingIntent pendingIntentMiku;

  private PendingIntent pendingIntentYes;

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
   * Android information.
   */
  private Information information;

  private Integer mode;

  public PendingIntent getPendingIntent() {
    return pendingIntentMiku;
  }

  public ConcurrentLinkedQueue<NicovideoEntry> getNicoEntryQueue() {
    return nicoEntryQueue;
  }

  public ConcurrentHashMap<Integer, Bitmap> getImages() {
    return images;
  }

  public Information getInformation() {
    return information;
  }

  /**
   * Thread-safe setter. Change widget mode immediately.
   * 
   * @param mode
   */
  public synchronized void setMode(int mode) {
    this.mode = mode;
  }

}
