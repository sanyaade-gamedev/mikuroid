package org.naga.project.android.mikuroid.widget;

import java.util.List;

import org.naga.project.android.network.NetworkManager;
import org.naga.project.android.task.DownloadImageTask;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import org.naga.project.android.mikuroid.R;
import org.naga.project.nicovideo.NicovideoEntry;
import org.naga.project.nicovideo.NicovideoNetwork;
import org.naga.project.nicovideo.NicovideoUtil;

public class MikuHatsune extends WidgetCharacter {

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    this.messageQueue.add("みっくみっくにしてあげる～♪");

    List<NicovideoEntry> entryList = NicovideoNetwork
        .requestDailyRankingVOCALOID();
    this.nicoEntryQueue.addAll(entryList);

    new DownloadImageTask().execute(NicovideoUtil
        .generateThumbnailUrls(entryList));
  }

  public void update() {
    // Finish speaking and show all message.
    if (this.talking) {
      this.talkHandler.sendEmptyMessage(WidgetCharacter.TALK_STOP);
      return;
    }

    this.initTalk();

    this.currentMessage = this.messageQueue.poll();
    if (null == this.currentMessage) {
      this.currentMessage = "";
      return;
    }

    this.talking = true;

    this.talkHandler.sendEmptyMessage(WidgetCharacter.TALK_MESSAGE);
  }

  public void view(RemoteViews views) {
    if (this.message.length() == 0) {
      views.setViewVisibility(R.id.baloon0, ImageView.INVISIBLE);

      Bitmap bitmap = NetworkManager.getInstance().load(
          "http://tn-skr2.smilevideo.jp/smile?i=13136668");

      if (null != bitmap) {
        views.setViewVisibility(R.id.nicovideo_image, ImageView.VISIBLE);
        views.setImageViewBitmap(R.id.nicovideo_image, bitmap);
      }

    } else {
      views.setViewVisibility(R.id.nicovideo_image, ImageView.VISIBLE);
      views.setTextViewText(R.id.miku_message, this.message.toString());
    }
  }

}
