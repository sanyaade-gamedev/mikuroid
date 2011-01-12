package org.naga.project.android.menu;

import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class MenuYesNo {

  public static final int NONE = 0;
  public static final int YES = 1;
  public static final int NO = 2;

  public int update(Intent intent) {
    if (null != intent && null != intent.getAction()) {
      // Yes or No button was clicked.
      if (MikuroidIntent.ACTION_YES.equals(intent.getAction())) {
        // Clicked yes.
        return MenuYesNo.YES;
      } else if (MikuroidIntent.ACTION_NO.equals(intent.getAction())) {
        // Clicked no.
        return MenuYesNo.NO;
      }
    }

    return MenuYesNo.NONE;
  }

  public void view(RemoteViews views) {
    views.setViewVisibility(R.id.yesno, ImageView.VISIBLE);

    views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);
  }

}
