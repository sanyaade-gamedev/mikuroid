package org.naga.project.android.mikuroid.widget.action;

import org.naga.project.android.message.MessageTalk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.character.MikuMessage;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.scene.Scene;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class ElectricPowerUsageAction extends Action {

  public ElectricPowerUsageAction(Scene s) {
    super(s);
  }

  public boolean create() {
    this.messageTalk = new MessageTalk(this.scene, 100, 100);

    this.messageTalk.messageQueue.addAll(MikuMessage
        .generateElectricPowerUsageMessage());

    return true;
  }

  @Override
  public boolean update(Intent intent) {
    this.talkResult = this.messageTalk.execute();

    if (this.talkResult == MessageTalk.NOTHING) {
      return false;
    }

    return true;
  }

  @Override
  public void view(RemoteViews views) {
    if (MessageTalk.TALKING == this.talkResult
        || MessageTalk.SHOW_ALL == this.talkResult) {
      views.setViewVisibility(R.id.balloon_layout, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message,
          this.messageTalk.message.toString());
    } else {
      views.setViewVisibility(R.id.balloon_layout, ImageView.INVISIBLE);
    }

    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno_layout, ImageView.INVISIBLE);
  }

  private MessageTalk messageTalk;

  private int talkResult;

}
