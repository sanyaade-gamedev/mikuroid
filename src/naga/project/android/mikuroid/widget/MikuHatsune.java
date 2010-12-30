package naga.project.android.mikuroid.widget;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import naga.project.android.mikuroid.R;
import naga.project.android.network.NetworkManager;
import naga.project.android.network.NetworkNicovideo;

public class MikuHatsune extends WidgetCharacter {

  private static final int imageId = R.id.miku;

  private static final int messageId = R.id.miku_message;

  private static final int balloonId = R.id.baloon0;

  private static final int nicovideoImageId = R.id.nicovideo_image;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    this.messageQueue.add("みっくみっくにしてあげる～♪");

    this.nicoEntryQueue.addAll(NetworkNicovideo.requestDailyRankingVOCALOID());
  }

  public void update() {
    this.play();
  }

  public void view(RemoteViews views) {
    if (this.message.length() == 0) {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.INVISIBLE);

      Bitmap bitmap = NetworkManager.getInstance().load(
          "http://tn-skr2.smilevideo.jp/smile?i=13136668");

      if (null != bitmap) {
        views.setViewVisibility(MikuHatsune.nicovideoImageId, ImageView.VISIBLE);
        views.setImageViewBitmap(MikuHatsune.nicovideoImageId, bitmap);
      }
    } else {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.VISIBLE);
      views.setTextViewText(MikuHatsune.messageId, this.message.toString());
    }
  }

  public static int getImageid() {
    return imageId;
  }

}
