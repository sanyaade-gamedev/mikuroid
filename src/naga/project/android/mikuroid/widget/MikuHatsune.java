package naga.project.android.mikuroid.widget;

import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import naga.project.android.mikuroid.R;

public class MikuHatsune extends WidgetCharacter {

  private static final int imageId = R.id.miku;

  private static final int messageId = R.id.miku_message;

  private static final int balloonId = R.id.baloon0;

  private enum MikuSurfaces {
    NORMAL,
  }

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    this.currentSurface = MikuSurfaces.NORMAL;

    this.messageQueue.add("みっくみっくにしてあげる～♪");
    this.messageQueue.add("みっくみっくにしてやんよ～♪");
    this.messageQueue.add("みっくみっくにしてやるにゃ～♪");
    this.messageQueue.add("みっくみっくにしてほしい～♪");
  }

  public void update(RemoteViews views) {
    this.play();

    if (this.currentMessage.length() == 0) {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.INVISIBLE);
    } else {
      views.setViewVisibility(MikuHatsune.balloonId, ImageView.VISIBLE);
      views.setTextViewText(MikuHatsune.messageId, this.currentMessage.toString());
    }
  }

  public static int getImageid() {
    return imageId;
  }

  private MikuSurfaces currentSurface;

}
