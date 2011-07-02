package org.naga.project.android.mikuroid.widget.scene;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.character.MikuMessage;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.action.Action;
import org.naga.project.android.mikuroid.widget.action.ElectricPowerUsageAction;
import org.naga.project.android.mikuroid.widget.action.TalkAction;
import org.naga.project.android.mikuroid.widget.listener.ConfigureListener;
import org.naga.project.android.mikuroid.widget.listener.Listener;

import android.content.Intent;
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
    // Listen intent event.
    for (Listener listener : this.listenerList) {
      if (!listener.onListen(intent)) {
        // Do nothing when return false
        return;
      }
    }

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
        // Create Electric power usage action.
        ElectricPowerUsageAction action = new ElectricPowerUsageAction(this);
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
  }

  public void onView() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (this.waiting) {
      // View wait surface.
      WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

      views.setViewVisibility(R.id.yesno_layout, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.balloon_layout, ImageView.INVISIBLE);
    }

    if (null != this.currentAction) {
      this.currentAction.view(views);
    }

    views.setImageViewResource(MikuHatsune.ID_IMAGE_VIEW,
        WidgetManager.getInstance().miku.currentSurface);

    WidgetManager.getInstance().updateAppWidget(views);
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
