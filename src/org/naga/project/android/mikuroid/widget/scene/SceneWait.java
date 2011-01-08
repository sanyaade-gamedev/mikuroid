package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.character.MikuMessage;
import org.naga.project.android.mikuroid.widget.WidgetManager;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class SceneWait extends Scene {

  public SceneWait(Scene sc) {
    super(sc);

    this.talkResult = false;
  }

  @Override
  public boolean create() {
    this.talk = new Talk(this, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();

    // Add talk message.
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku1));
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku2));

    return true;
  }

  @Override
  protected void onUpdateProcess() {
    this.talkResult = this.talk.execute();

    if (!this.talkResult) {
      // Nothing to talk.

      // Create new scene or talk message.

      Resources res = WidgetManager.getInstance().getContext().getResources();

      this.talk.getMessageQueue().add(res.getString(R.string.mikumiku1));
      this.talk.getMessageQueue().addAll(
          MikuMessage.generateBatteryLevelMessage());
    }
  }

  @Override
  protected void onViewProcess() {
    RemoteViews views = new RemoteViews(WidgetManager.getInstance()
        .getContext().getPackageName(), R.layout.widget_miku);

    if (this.talkResult) {
      views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_ANGRY);

      views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.talk.getMessage()
          .toString());

      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
    } else {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
      views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);
    }

    WidgetManager.getInstance().updateAppWidget(views);
  }

  private Talk talk;

  private boolean talkResult;

}
