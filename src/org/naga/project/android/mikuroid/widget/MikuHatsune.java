package org.naga.project.android.mikuroid.widget;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import org.naga.project.android.message.Talk;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.widget.WidgetManager.WidgetMode;

public class MikuHatsune {

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");
  }

  public boolean create() {
    Log.d("MikuHatsune", "create()");

    this.mode = WidgetMode.TALK;

    this.talk = new Talk(WidgetManager.getInstance(), 100, 20);
    this.talk.getMessageQueue().add("みっくみっくにしてあげる～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてやんよ～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてあげる～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてやんよ～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてあげる～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてやんよ～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてあげる～♪");
    this.talk.getMessageQueue().add("みっくみっくにしてやんよ～♪");

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
    case TALK:
      this.talk.process();
      break;

    case NICOVIDEO_RANKING:
      // TODO View nicovideo ranking.
      break;
    }
  }

  public void view(RemoteViews views) {
    if (this.talk.getMessage().length() == 0) {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);

      /*
       * Bitmap bitmap = NetworkManager.getInstance().load(
       * "http://tn-skr2.smilevideo.jp/smile?i=13136668");
       * 
       * if (null != bitmap) { views.setViewVisibility(R.id.nicovideo_image,
       * ImageView.VISIBLE); views.setImageViewBitmap(R.id.nicovideo_image,
       * bitmap); }
       */

    } else {
      views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.talk.getMessage()
          .toString());
    }
  }

  private WidgetMode mode;

  private Talk talk;

}
