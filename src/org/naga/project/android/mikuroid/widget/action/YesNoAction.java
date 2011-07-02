package org.naga.project.android.mikuroid.widget.action;

import org.naga.project.android.message.MessageTalk;
import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.scene.Scene;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * 
 * @author reciente
 * 
 */
public class YesNoAction extends Action {

  private static final int SELECT_NONE = 1;
  private static final int SELECT_YES = 2;
  private static final int SELECT_NO = 2;

  public YesNoAction(Scene s) {
    super(s);
  }

  public boolean create() {
    this.messageTalk = new MessageTalk(this.scene, 100, 20);

    Resources res = WidgetManager.getInstance().getContext().getResources();
    this.messageTalk.messageQueue.add(res.getString(R.string.yesno));

    return true;
  }

  @Override
  public boolean update(Intent intent) {
    int selected = YesNoAction.SELECT_NONE;
    boolean result = true;

    if (null != intent && null != intent.getAction()) {
      // Yes or No button was clicked.
      if (MikuroidIntent.ACTION_YES.equals(intent.getAction())) {
        // Clicked yes.
        selected = YesNoAction.SELECT_YES;
      } else if (MikuroidIntent.ACTION_NO.equals(intent.getAction())) {
        // Clicked no.
        selected = YesNoAction.SELECT_NO;
      }
    }

    if (selected == YesNoAction.SELECT_YES) {
      Resources res = WidgetManager.getInstance().getContext().getResources();
      // Add talk message.
      TalkAction action = new TalkAction(this.scene);
      action.create(res.getString(R.string.mikumiku1));
      this.scene.setReserveAction(action);

      result = false;
    } else if (selected == YesNoAction.SELECT_NO) {
      Resources res = WidgetManager.getInstance().getContext().getResources();
      // Add talk message.
      TalkAction action = new TalkAction(this.scene);
      action.create(res.getString(R.string.mikumiku2));
      this.scene.setReserveAction(action);

      result = false;
    } else { // MenuYesNo.NOTHING
      // Not touched menu icon.
      this.messageTalk.executeNoInit();
    }

    return result;
  }

  @Override
  public void view(RemoteViews views) {
    views.setViewVisibility(R.id.yesno_layout, ImageView.VISIBLE);

    views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);

    views.setTextViewText(R.id.miku_message,
        this.messageTalk.message.toString());
    views.setViewVisibility(R.id.balloon_layout, ImageView.VISIBLE);
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_ANGRY;
  }

  private MessageTalk messageTalk;

}
