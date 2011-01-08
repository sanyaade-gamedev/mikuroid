package org.naga.project.android.mikuroid.character;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import org.naga.project.android.mikuroid.R;

public class MikuHatsune {

  public static final int SURFACE_SURPRISED = R.drawable.mikuroid001;
  public static final int SURFACE_ANGRY = R.drawable.mikuroid002;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");
  }

  public boolean create() {
    Log.d("MikuHatsune", "create()");

    /*
    Resources res = WidgetManager.getInstance().getContext().getResources();
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku1));
    this.talk.getMessageQueue().add(res.getString(R.string.mikumiku2));
    */

    /*
     * List<NicovideoEntry> entryList = NicovideoNetwork
     * .requestDailyRankingVOCALOID(); this.nicoEntryQueue.addAll(entryList);
     * 
     * new DownloadImageTask().execute(NicovideoUtil
     * .generateThumbnailUrls(entryList));
     */

    return true;
  }

  /*
   * Bitmap bitmap = NetworkManager.getInstance().load(
   * "http://tn-skr2.smilevideo.jp/smile?i=13136668");
   * 
   * if (null != bitmap) { views.setViewVisibility(R.id.nicovideo_image,
   * ImageView.VISIBLE); views.setImageViewBitmap(R.id.nicovideo_image, bitmap);
   * }
   */

  public void waitUpdate() {
    /*
    // Add talk message.
    String message = MikuMessage.generateBatteryLevelMessage();
    if (null != message) {
      // Finish to talk.
      // Change widget mode immediately.
      WidgetManager.getInstance().setMode(WidgetManager.WIDGET_MODE_TALK);
      this.talk.getMessageQueue().add(message);
      this.talk.process();
    }
    */
  }

  public void waitView(RemoteViews views) {
    views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);

    views.setViewVisibility(R.id.yesno, ImageView.VISIBLE);
  }

  public void talkUpdate() {
    /*
    if (!this.talk.process()) {
      // Nothing to talk.
      // Change widget mode immediately.
      WidgetManager.getInstance().setMode(WidgetManager.WIDGET_MODE_WAIT);
    }
    */
  }

  public void talkView(RemoteViews views) {
    /*
    if (this.talk.getMessage().length() == 0) {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);
    } else {
      views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_ANGRY);

      views.setViewVisibility(R.id.nicovideo_image, ImageView.INVISIBLE);
      views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.talk.getMessage()
          .toString());

      views.setViewVisibility(R.id.yesno, ImageView.INVISIBLE);
    }
    */
  }

}
