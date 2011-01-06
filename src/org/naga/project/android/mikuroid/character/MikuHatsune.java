package org.naga.project.android.mikuroid.character;

import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.WidgetObject;

public class MikuHatsune {

  private static final int SURFACE_SURPRISED = R.drawable.mikuroid001;
  private static final int SURFACE_ANGRY = R.drawable.mikuroid002;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");
  }

  public boolean create() {
    Log.d("MikuHatsune", "create()");

    this.mode = WidgetObject.WidgetMode.TALK;

    this.talk = new Talk(WidgetManager.getInstance(), 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku1));
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku2));

    /*
     * List<NicovideoEntry> entryList = NicovideoNetwork
     * .requestDailyRankingVOCALOID(); this.nicoEntryQueue.addAll(entryList);
     * 
     * new DownloadImageTask().execute(NicovideoUtil
     * .generateThumbnailUrls(entryList));
     */

    return true;
  }

  public void update() {
    switch (this.mode) {
    case WAIT:
      this.waitUpdate();
      break;

    case TALK:
      this.talkUpdate();

      break;

    case NICOVIDEO_RANKING:
      // TODO Update nicovideo ranking process.
      break;
    }
  }

  public void view(RemoteViews views) {
    switch (this.mode) {
    case WAIT:
      this.waitView(views);

    case TALK:
      this.talkView(views);
      break;

    case NICOVIDEO_RANKING:
      // TODO View nicovideo ranking.
      break;
    }

    /*
     * Bitmap bitmap = NetworkManager.getInstance().load(
     * "http://tn-skr2.smilevideo.jp/smile?i=13136668");
     * 
     * if (null != bitmap) { views.setViewVisibility(R.id.nicovideo_image,
     * ImageView.VISIBLE); views.setImageViewBitmap(R.id.nicovideo_image,
     * bitmap); }
     */
  }

  private void waitUpdate() {
    // Add talk message.
    String message = MikuMessage.generateBatteryMessage();
    if (null != message) {
      // Finish to talk.
      this.mode = WidgetObject.WidgetMode.TALK;
      this.talk.getMessageQueue().add(message);
      this.talk.process();
    }
  }

  private void waitView(RemoteViews views) {
    views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);
  }

  private void talkUpdate() {
    if (!this.talk.process()) {
      // Nothing to talk.
      this.mode = WidgetObject.WidgetMode.WAIT;
    }
  }

  static int count = 0;

  private void talkView(RemoteViews views) {
    if (this.talk.getMessage().length() == 0) {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    } else {
      ++count;
      if (count > 2) {
        views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_ANGRY);
        if (count > 4) {
          count = 0;
        }
      } else {
        views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);
      }

      views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.talk.getMessage()
          .toString());
    }
  }

  private WidgetObject.WidgetMode mode;

  private Talk talk;

}
