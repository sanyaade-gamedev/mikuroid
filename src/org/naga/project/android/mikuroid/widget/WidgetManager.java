package org.naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.scene.Scene;
import org.naga.project.android.mikuroid.widget.scene.SceneWait;
import org.naga.project.nicovideo.NicovideoEntry;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

public class WidgetManager {

  public static final int WIDGET_MODE_NONE = 0;
  public static final int WIDGET_MODE_WAIT = 1;
  public static final int WIDGET_MODE_TALK = 2;
  public static final int WIDGET_MODE_NICOVIDEO_RANKING = 3;
  public static final int WIDGET_MODE_YESNO = 4;

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

    if (null == this.mainScene) {
      // Set start up scene.
      this.mainScene = new SceneWait(null);
      this.mainScene.create();
    }

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
    intentMiku.setAction(MikuroidIntent.ACTION_MIKU_TOUCH);
    this.pendingIntentMiku = PendingIntent.getService(this.context, 0,
        intentMiku, PendingIntent.FLAG_UPDATE_CURRENT);

    Intent intentYes = new Intent();
    intentYes.setAction(MikuroidIntent.ACTION_YES);
    this.pendingIntentYes = PendingIntent.getService(this.context, 0,
        intentYes, PendingIntent.FLAG_UPDATE_CURRENT);

    Intent intentNo = new Intent();
    intentNo.setAction(MikuroidIntent.ACTION_NO);
    this.pendingIntentNo = PendingIntent.getService(this.context, 0, intentNo,
        PendingIntent.FLAG_UPDATE_CURRENT);

    this.widget = new ComponentName(this.context, WidgetProvider.class);
    this.appWidgetManager = AppWidgetManager.getInstance(this.context);
  }

  public void execute(Intent intent) {
    this.mainScene.onUpdate();
    this.mainScene.onView();
  }

  public void updateAppWidget(RemoteViews views) {
    // Set pending intent to check has miku clicked.
    views.setOnClickPendingIntent(R.id.miku, this.pendingIntentMiku);
    views.setOnClickPendingIntent(R.id.yes, this.pendingIntentYes);
    views.setOnClickPendingIntent(R.id.no, this.pendingIntentNo);

    this.appWidgetManager.updateAppWidget(this.widget, views);
  }

  /**
   * Context of WidgetProvider.
   */
  private Context context;

  /**
   * Pending intent to set intent action. Miku ImageView intent.
   */
  private PendingIntent pendingIntentMiku;

  /**
   * Pending intent to set intent action. Yes ImageButton intent.
   */
  private PendingIntent pendingIntentYes;

  /**
   * Pending intent to set intent action. No ImageButton intent.
   */
  private PendingIntent pendingIntentNo;

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
   * Widget main scene.
   */
  private Scene mainScene;

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

  public Context getContext() {
    return context;
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

  public PendingIntent getPendingIntentYes() {
    return pendingIntentYes;
  }

  public PendingIntent getPendingIntentNo() {
    return pendingIntentNo;
  }

}
