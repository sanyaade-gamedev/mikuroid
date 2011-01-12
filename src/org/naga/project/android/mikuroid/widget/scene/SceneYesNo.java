package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneYesNo extends Scene {

  public SceneYesNo(Scene sc, String msg) {
    super(sc);
    this.message = msg;
  }

  @Override
  public boolean create() {
    Log.d("SceneYesNo", "create");

    if (null != this.message) {
      this.talk = new Talk(this, 100, 20);

      this.talk.messageQueue.add(this.message);
    }

    this.talkResult = Talk.NOTHING;

    return true;
  }

  @Override
  protected void onUpdateProcess(Intent intent) {
    if (null != this.talk) {
      this.talkResult = this.talk.execute();
    }

    if (Talk.NOTHING == this.talkResult) {
      // Nothing to talk.
    }

    if (null != intent && null != intent.getAction()) {
      // Yes or No button was clicked.
      if (MikuroidIntent.ACTION_YES.equals(intent.getAction())) {
        // Clicked yes.
      } else if (MikuroidIntent.ACTION_NO.equals(intent.getAction())) {
        // Clicked no.
      }
    }
  }

  @Override
  protected void onViewProcess() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (null != this.talk) {
      if (Talk.TALKING == this.talkResult || Talk.SHOW_ALL == this.talkResult) {
        views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
        views.setTextViewText(R.id.miku_message, this.talk.message.toString());
      }
      // Not invisible question message.
    } else {
      // Invisible message balloon when question not set.
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    }

    views.setViewVisibility(R.id.yesno, ImageView.VISIBLE);

    WidgetManager.getInstance().updateAppWidget(views);
  }

  private Talk talk;

  private String message;

  private int talkResult;

}
