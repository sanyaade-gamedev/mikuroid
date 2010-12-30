package naga.project.android.mikuroid.widget;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import naga.project.android.mikuroid.R;
import naga.project.android.nicovideo.NicovideoRequest;

public class MikuHatsune extends WidgetCharacter {

  private static final int imageId = R.id.miku;

  private static final int messageId = R.id.miku_message;

  private static final int balloonId = R.id.baloon0;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    this.messageQueue.add("みっくみっくにしてあげる～♪");

    NicovideoRequest.requestDailyRankingVOCALOID();
  }

  public void update() {
    this.play();
  }

  public void view(RemoteViews views) {
    if (this.message.length() == 0) {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.INVISIBLE);
    } else {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.VISIBLE);
      views.setTextViewText(MikuHatsune.messageId, this.message.toString());
    }
  }

  public static int getImageid() {
    return imageId;
  }

}
