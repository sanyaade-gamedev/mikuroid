package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.menu.MenuYesNo;
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

public class SceneWait extends Scene {

  static final int FORCE_WAIT = 1;

  public SceneWait(Scene sc) {
    super(sc);

    this.talkResult = MessageTalk.NOTHING;
    this.menuYesNo = null;
    this.waiting = false;
  }

  @Override
  public boolean create() {
    this.talk = new MessageTalk(this, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();

    // Add talk message.
    this.talk.messageQueue.add(res.getString(R.string.mikumiku1));
    this.talk.messageQueue.add(res.getString(R.string.mikumiku2));

    return true;
  }

  @Override
  protected void onUpdateProcess(Intent intent) {
    if (null != this.menuYesNo) {
      this.waiting = false;

      int menuResult = this.menuYesNo.update(intent);

      if (MenuYesNo.YES == menuResult) {
        this.talk.init();
        this.menuYesNo = null;

        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.talk.messageQueue.add(res.getString(R.string.mikumiku1));
        this.talkResult = this.talk.execute();
      } else if (MenuYesNo.NO == menuResult) {
        this.talk.init();
        this.menuYesNo = null;

        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.talk.messageQueue.add(res.getString(R.string.mikumiku2));
        this.talkResult = this.talk.execute();
      } else { // MenuYesNo.NOTHING
        // Not touched menu icon.
        this.talkResult = this.talk.executeNoInit();
      }
    } else {
      this.talkResult = this.talk.execute();
    }

    if (MessageTalk.NOTHING == this.talkResult) {
      if (null == this.menuYesNo && this.waiting) {
        // TEST Create menu.
        this.menuYesNo = new MenuYesNo();
        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.talk.messageQueue.add(res.getString(R.string.yesno));

        // Show message.
        this.talkResult = this.talk.execute();
      } else {
        this.waiting = true;
      }
    }
  }

  @Override
  protected void onViewProcess() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (this.waiting) {
      this.waitView(views);
    }

    if (null != this.menuYesNo) {
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

  private void waitView(RemoteViews views) {
    // View wait surface.
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
  }

  private void yesnoView(RemoteViews views) {
    this.menuYesNo.view(views);
    views.setTextViewText(R.id.miku_message, this.talk.message.toString());
    views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_ANGRY;
  }

  private void talkVIew(RemoteViews views) {
    if (MessageTalk.TALKING == this.talkResult
        || MessageTalk.SHOW_ALL == this.talkResult) {
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.talk.message.toString());
    } else {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    }

    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
  }

  /**
   * Handler to clear view.
   */
  private Handler handler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case SceneWait.FORCE_WAIT:
        waiting = true;
        break;
      }
    }

  };

  /**
   * Use to talk.
   */
  private MessageTalk talk;

  private int talkResult;

  private MenuYesNo menuYesNo;

  private boolean waiting;

}
