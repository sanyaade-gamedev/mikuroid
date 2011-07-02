package org.naga.project.android.mikuroid.widget;

import org.naga.project.android.Information;
import org.naga.project.android.mikuroid.MikuroidConfigure;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.scene.Scene;
import org.naga.project.android.mikuroid.widget.scene.SceneWait;
import org.naga.project.yahoo.dev.ElectricPowerUsageInformation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetManager {

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

    this.information = new Information();
    this.epuInformation = new ElectricPowerUsageInformation();

    this.currentScene = null;
  }

  public boolean create() {
    // create will call any times.
    // so check null to not overload object.
    if (null == this.miku) {
      this.miku = new MikuHatsune();
      this.miku.create();
    }

    if (null == this.currentScene) {
      // Set start up scene.
      this.currentScene = new SceneWait();
      this.currentScene.create();
    }

    return true;
  }

  public void execute(Intent intent) {
    this.currentScene.onUpdate(intent);
    this.currentScene.onView();
  }

  public void updateAppWidget(RemoteViews views) {
    // Set pending intent to check has miku clicked.
    views.setOnClickPendingIntent(R.id.miku, this.pendingIntentMiku);
    views.setOnClickPendingIntent(R.id.yes, this.pendingIntentYes);
    views.setOnClickPendingIntent(R.id.no, this.pendingIntentNo);
    views.setOnClickPendingIntent(R.id.configure, this.pendingIntentConfigure);

    this.appWidgetManager.updateAppWidget(this.widget, views);
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

    // Yes button.
    Intent intentYes = new Intent();
    intentYes.setAction(MikuroidIntent.ACTION_YES);
    this.pendingIntentYes = PendingIntent.getService(this.context, 0,
        intentYes, PendingIntent.FLAG_UPDATE_CURRENT);

    // No button.
    Intent intentNo = new Intent();
    intentNo.setAction(MikuroidIntent.ACTION_NO);
    this.pendingIntentNo = PendingIntent.getService(this.context, 0, intentNo,
        PendingIntent.FLAG_UPDATE_CURRENT);

    // Configure button.
    Intent intentConfigure = new Intent();
    intentConfigure.setAction(MikuroidIntent.ACTION_CONFIGURE);
    this.pendingIntentConfigure = PendingIntent.getService(this.context, 0,
        intentConfigure, PendingIntent.FLAG_UPDATE_CURRENT);

    this.widget = new ComponentName(this.context, WidgetProvider.class);
    this.appWidgetManager = AppWidgetManager.getInstance(this.context);
  }

  public Context getContext() {
    return context;
  }

  /**
   * Widget character Miku Hatsune.
   */
  public MikuHatsune miku;

  /**
   * Widget configure.
   */
  public MikuroidConfigure config;

  /**
   * Android information.
   */
  public Information information;

  /**
   * ElectricPowerUsageInformation
   */
  public ElectricPowerUsageInformation epuInformation;

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
   * Pending intent to set intent action. Configure ImageButton intent.
   */
  private PendingIntent pendingIntentConfigure;

  /**
   * Widget component name.
   */
  private ComponentName widget;

  /**
   * AppWidgetManager to manage this widget.
   */
  private AppWidgetManager appWidgetManager;

  /**
   * Widget main scene.
   */
  private Scene currentScene;

}
