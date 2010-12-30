package naga.project.android.mikuroid.widget;

import java.util.List;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import naga.project.android.mikuroid.R;
import naga.project.android.network.DownloadImageTask;
import naga.project.android.network.NetworkNicovideo;
import naga.project.android.nicovideo.NicovideoEntry;
import naga.project.android.nicovideo.NicovideoUtil;

public class MikuHatsune extends WidgetCharacter {

  private static final int imageId = R.id.miku;

  private static final int messageId = R.id.miku_message;

  private static final int balloonId = R.id.baloon0;

  private static final int nicovideoImageId = R.id.nicovideo_image;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    //this.messageQueue.add("みっくみっくにしてあげる～♪");

    List<NicovideoEntry> entryList = NetworkNicovideo.requestDailyRankingVOCALOID();
    this.nicoEntryQueue.addAll(entryList);
    
    new DownloadImageTask().execute(NicovideoUtil.generateThumbnailUrls(entryList));
  }

  public void update() {
    this.play();
  }

  public void view(RemoteViews views) {
    if (this.message.length() == 0) {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.INVISIBLE);

      /*
      Bitmap bitmap = NetworkManager.getInstance().load(
          "http://tn-skr2.smilevideo.jp/smile?i=13136668");

      if (null != bitmap) {
        views.setViewVisibility(MikuHatsune.nicovideoImageId, ImageView.VISIBLE);
        views.setImageViewBitmap(MikuHatsune.nicovideoImageId, bitmap);
      }
      */
    } else {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.VISIBLE);
      views.setTextViewText(MikuHatsune.messageId, this.message.toString());
    }
  }

  public static int getImageid() {
    return imageId;
  }

}
