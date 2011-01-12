package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.menu.MenuYesNo;
import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneWait extends Scene {

  public SceneWait(Scene sc) {
    super(sc);

    this.talkResult = Talk.NOTHING;
    this.menuYesNo = null;
  }

  @Override
  public boolean create() {
    this.talk = new Talk(this, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();

    // Add talk message.
    this.talk.messageQueue.add(res.getString(R.string.mikumiku1));
    this.talk.messageQueue.add(res.getString(R.string.mikumiku2));

    return true;
  }

  @Override
  protected void onUpdateProcess(Intent intent) {

    if (null != this.menuYesNo) {
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

    if (Talk.NOTHING == this.talkResult) {
      if (null == this.menuYesNo) {
        // TEST Create menu.
        this.menuYesNo = new MenuYesNo();
        Resources res = WidgetManager.getInstance().getContext().getResources();
        this.talk.messageQueue.add(res.getString(R.string.yesno));

        // Show message.
        this.talkResult = this.talk.execute();
      }
    }
  }

  @Override
  protected void onViewProcess() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    MikuHatsune miku = WidgetManager.getInstance().miku;

    if (null != this.menuYesNo) {
      this.menuYesNo.view(views);
      views.setTextViewText(R.id.miku_message, this.talk.message.toString());
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      miku.activeSurface = MikuHatsune.SURFACE_ANGRY;
    } else {
      if (Talk.TALKING == this.talkResult || Talk.SHOW_ALL == this.talkResult) {
        views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
        views.setTextViewText(R.id.miku_message, this.talk.message.toString());
      } else {
        views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
      }

      miku.activeSurface = MikuHatsune.SURFACE_NORMAL;

      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
    }

    views.setImageViewResource(MikuHatsune.ID_IMAGE_VIEW, miku.activeSurface);

    // Invisible not using widgets.
    views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);

    WidgetManager.getInstance().updateAppWidget(views);
  }

  /**
   * Use to talk.
   */
  private Talk talk;

  private int talkResult;

  private MenuYesNo menuYesNo;

}
