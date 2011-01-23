package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.action.Action;
import org.naga.project.android.mikuroid.widget.action.TalkAction;
import org.naga.project.android.mikuroid.widget.action.YesNoAction;

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
    this.currentAction = new TalkAction(this);
    this.currentAction.create();

    return true;
  }

  public void onUpdate(Intent intent) {
    if (null != this.currentAction) {
      this.waiting = false;

      if (!this.currentAction.update(intent)) {
        // If return false, delete current action.
        this.currentAction = null;

        if (null != this.reservedAction) {
          this.currentAction = this.reservedAction;
          this.reservedAction = null;

          this.currentAction.create();
          this.currentAction.update(intent);
        }
      }
    }

    // Create new actions in here.

    // Create Yes No select action.
    if (null == this.currentAction && this.waiting) {
      // TEST Create menu.
      this.currentAction = new YesNoAction(this);
      this.currentAction.create();
      this.currentAction.update(intent);
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

      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
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

  private Action currentAction;

  private Action reservedAction;

  private boolean waiting;

  public void setReserveAction(Action action) {
    this.reservedAction = action;
  }

}
