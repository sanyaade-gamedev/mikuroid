package naga.project.android.mikuroid.widget;

import android.widget.ImageView;
import android.widget.RemoteViews;
import naga.project.android.mikuroid.R;

public class MikuHatsune extends WidgetCharacter {

    private static final int imageId = R.id.miku;

    private static final int messageId = R.id.miku_message;

    private static final int balloonId = R.id.baloon0;

    public MikuHatsune() {
        this.message.setLength(0);
        this.message.append("みっくみっく♪");
    }

    public void updateRemoteViews(RemoteViews views) {
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
