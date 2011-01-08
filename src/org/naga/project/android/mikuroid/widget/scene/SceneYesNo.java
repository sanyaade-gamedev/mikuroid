package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;

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
  protected void onUpdateProcess() {
    if (!this.talk.process()) {
      // Nothing to talk.
      // This scene process is finished.
      this.finish = true;
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
