package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.action.Action;
import org.naga.project.android.action.MenuYesNo;
import org.naga.project.android.message.MessageTalk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneWait implements Scene {

  public SceneWait() {
    this.talkResult = MessageTalk.NOTHING;
    this.currentAction = null;
    this.waiting = false;
  }

  public boolean create() {
    this.messageTalk = new MessageTalk(this, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();

    // Add talk message.
    this.messageTalk.messageQueue.add(res.getString(R.string.mikumiku1));
    this.messageTalk.messageQueue.add(res.getString(R.string.mikumiku2));

    return true;
  }

  public void onUpdate(Intent intent) {
    if (null != this.currentAction) {
      this.waiting = false;

      int menuResult = this.currentAction.update(intent);

      if (MenuYesNo.YES == menuResult) {
        this.messageTalk.init();
        this.currentAction = null;

        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.messageTalk.messageQueue.add(res.getString(R.string.mikumiku1));
        this.talkResult = this.messageTalk.execute();
      } else if (MenuYesNo.NO == menuResult) {
        this.messageTalk.init();
        this.currentAction = null;

        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.messageTalk.messageQueue.add(res.getString(R.string.mikumiku2));
        this.talkResult = this.messageTalk.execute();
      } else { // MenuYesNo.NOTHING
        // Not touched menu icon.
        this.talkResult = this.messageTalk.executeNoInit();
      }
    } else {
      this.talkResult = this.messageTalk.execute();
    }

    if (MessageTalk.NOTHING == this.talkResult) {
      // Create new actions in here.

      // Create Yes No select action.
      if (null == this.currentAction && this.waiting) {
        // TEST Create menu.
        this.currentAction = new MenuYesNo(this);
        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.messageTalk.messageQueue.add(res.getString(R.string.yesno));

        // Show message.
        this.talkResult = this.messageTalk.execute();
      } else {
        this.waiting = true;
      }
    }
  }

  public void onView() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (this.waiting) {
      this.waitView(views);
    }

    if (null != this.currentAction) {
      this.yesnoView(views);
    } else {
      this.talkVIew(views);
    }

    views.setImageViewResource(MikuHatsune.ID_IMAGE_VIEW,
        WidgetManager.getInstance().miku.currentSurface);

    // Invisible not using widgets.
    views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);

    WidgetManager.getInstance().updateAppWidget(views);
  }

  /**
   * View process when waiting mode.
   * 
   * @param views
   */
  private void waitView(RemoteViews views) {
    // View wait surface.
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
  }

  /**
   * View process when waiting yes no select action.
   * 
   * @param views
   */
  private void yesnoView(RemoteViews views) {
    this.currentAction.view(views);
    views.setTextViewText(R.id.miku_message,
        this.messageTalk.message.toString());
    views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_ANGRY;
  }

  /**
   * View process when talking mode.
   * 
   * @param views
   */
  private void talkVIew(RemoteViews views) {
    if (MessageTalk.TALKING == this.talkResult
        || MessageTalk.SHOW_ALL == this.talkResult) {
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message,
          this.messageTalk.message.toString());
    } else {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    }

    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
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

  /**
   * Use to talk.
   */
  private MessageTalk messageTalk;

  public MessageTalk getMessageTalk() {
    return messageTalk;
  }

  private int talkResult;

  private Action currentAction;

  private boolean waiting;

}
