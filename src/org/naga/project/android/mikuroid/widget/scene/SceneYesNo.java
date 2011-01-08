package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneYesNo extends Scene {

  public SceneYesNo(Scene sc) {
    super(sc);
  }

  @Override
  public boolean create() {
    Log.d("SceneYesNo", "create");

    this.talk = new Talk(this, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();
    // TODO test code. Constant message.
    this.talk.getMessageQueue().add(res.getString(R.string.yesno));

    return true;
  }

  @Override
  protected void onUpdateProcess(Intent intent) {
    int talkResult = this.talk.execute();
    
    if (Talk.NOTHING == talkResult) {
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

    views.setViewVisibility(R.id.yesno, ImageView.VISIBLE);

    WidgetManager.getInstance().updateAppWidget(views);
  }

  private Talk talk;

}
