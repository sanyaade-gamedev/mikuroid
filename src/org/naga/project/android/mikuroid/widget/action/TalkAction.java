package org.naga.project.android.mikuroid.widget.action;

import java.util.List;

import org.naga.project.android.message.MessageTalk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.scene.Scene;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class TalkAction extends Action {

  public TalkAction(Scene s) {
    super(s);

    this.talkResult = MessageTalk.NOTHING;
  }

  public boolean create(List<String> messageList) {
    if (null == messageList) {
      return false;
    }

    this.messageTalk = new MessageTalk(this.scene, 100, 20);
    this.messageTalk.messageQueue.addAll(messageList);

    return true;
  }

  public boolean create(String message) {
    if (null == message) {
      return false;
    }

    this.messageTalk = new MessageTalk(this.scene, 100, 20);
    this.messageTalk.messageQueue.add(message);

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
      views.setViewVisibility(R.id.baloon_layout, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message,
          this.messageTalk.message.toString());
    } else {
      views.setViewVisibility(R.id.baloon_layout, ImageView.INVISIBLE);
    }

    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_NORMAL;

    views.setViewVisibility(R.id.yesno_layout, ImageView.INVISIBLE);
  }

  private MessageTalk messageTalk;

  private int talkResult;

}
