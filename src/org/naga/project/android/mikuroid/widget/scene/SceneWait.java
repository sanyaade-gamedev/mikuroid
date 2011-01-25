package org.naga.project.android.mikuroid.widget.scene;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.character.MikuMessage;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.action.Action;
import org.naga.project.android.mikuroid.widget.action.TalkAction;
import org.naga.project.android.mikuroid.widget.action.YesNoAction;
import org.naga.project.android.mikuroid.widget.listener.ConfigureListener;
import org.naga.project.android.mikuroid.widget.listener.Listener;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneWait implements Scene {

  public SceneWait() {
    this.currentAction = null;
    this.waiting = false;
  }

  public boolean create() {
    TalkAction action = new TalkAction(this);
    action.create(WidgetManager.getInstance().getContext().getResources()
        .getString(R.string.message001));
    this.currentAction = action;

    this.listenerList = new ArrayList<Listener>();
    this.listenerList.add(new ConfigureListener());

    return true;
  }

  public void onUpdate(Intent intent) {
    if (this.currentAction != null) {
      this.waiting = false;

      if (!this.currentAction.update(intent)) {
        // If return false, delete current action.
        this.currentAction = null;

        if (null != this.reservedAction) {
          this.currentAction = this.reservedAction;
          this.reservedAction = null;

          this.currentAction.update(intent);
        }
      }
    }

    if (this.currentAction == null && this.waiting) {
      // Create new actions in here.
      SceneWait.count++;

      if (SceneWait.count % 2 == 0) {
        // Create Yes No select action.
        YesNoAction action = new YesNoAction(this);
        action.create();
        this.currentAction = action;
        this.currentAction.update(intent);

        SceneWait.count = 0;
      } else {
        TalkAction action = new TalkAction(this);
        action.create(MikuMessage.generateBatteryLevelMessage());
        this.currentAction = action;
        this.currentAction.update(intent);
      }
    } else {
      this.waiting = true;
    }

    // Listen intent event.
    for (Listener listener : this.listenerList) {
      listener.onListen(intent);
    }
  }

  public void onView() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (this.waiting) {
      // View wait surface.
      WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

      views.setViewVisibility(R.id.yesno_layout, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon_layout, ImageView.INVISIBLE);
    }

    if (null != this.currentAction) {
      this.currentAction.view(views);
    }

    views.setImageViewResource(MikuHatsune.ID_IMAGE_VIEW,
        WidgetManager.getInstance().miku.currentSurface);

    // Invisible not using widgets.
    views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);

    WidgetManager.getInstance().updateAppWidget(views);
  }

  static final int FORCE_WAIT = 1;

  /**
   * Handler to clear view.
   */
  private Handler handler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case SceneWait.FORCE_WAIT:
        // Set waiting mode.
        waiting = true;
        // Clear view.
        onView();
        break;
      }
    }

  };

  private void clearHandler() {
    this.handler.removeMessages(SceneWait.FORCE_WAIT);
  }

  private static int count = 0;

  private Action currentAction;

  private Action reservedAction;

  private boolean waiting;

  private List<Listener> listenerList;

  public void setReserveAction(Action action) {
    this.reservedAction = action;
  }

}
