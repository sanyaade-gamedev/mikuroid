package org.naga.project.android.mikuroid.widget.action;

import org.naga.project.android.mikuroid.MikuroidIntent;
import org.naga.project.android.mikuroid.R;
import org.naga.project.android.mikuroid.character.MikuHatsune;
import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.android.mikuroid.widget.scene.Scene;
import org.naga.project.android.mikuroid.widget.scene.SceneWait;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * Dependent on SceneWait
 * 
 * @author reciente
 * 
 */
public class YesNoAction extends Action {

  private static final int NONE = 0;
  private static final int YES = 1;
  private static final int NO = 2;

  public YesNoAction(Scene s) {
    super(s);
  }

  public boolean update(Intent intent) {
    int result = YesNoAction.NONE;

    if (null != intent && null != intent.getAction()) {
      // Yes or No button was clicked.
      if (MikuroidIntent.ACTION_YES.equals(intent.getAction())) {
        // Clicked yes.
        result = YesNoAction.YES;
      } else if (MikuroidIntent.ACTION_NO.equals(intent.getAction())) {
        // Clicked no.
        result = YesNoAction.NO;
      }
    }

    SceneWait sceneWait = (SceneWait) this.scene;

    if (result == YesNoAction.YES) {
      sceneWait.messageTalk.init();
      sceneWait.currentAction = null;

      Resources res = WidgetManager.getInstance().getContext().getResources();
      sceneWait.messageTalk.messageQueue.add(res.getString(R.string.mikumiku1));
      sceneWait.talkResult = sceneWait.messageTalk.execute();
    } else if (result == YesNoAction.NO) {
      sceneWait.messageTalk.init();
      sceneWait.currentAction = null;

      Resources res = WidgetManager.getInstance().getContext().getResources();
      sceneWait.messageTalk.messageQueue.add(res.getString(R.string.mikumiku2));
      sceneWait.talkResult = sceneWait.messageTalk.execute();
    } else { // MenuYesNo.NOTHING
      // Not touched menu icon.
      sceneWait.talkResult = sceneWait.messageTalk.executeNoInit();
    }

    return true;
  }

  public void view(RemoteViews views) {
    SceneWait sceneWait = (SceneWait) this.scene;

    views.setViewVisibility(R.id.yesno, ImageView.VISIBLE);

    views.setImageViewResource(R.id.miku, MikuHatsune.SURFACE_SURPRISED);

    views.setTextViewText(R.id.miku_message,
        sceneWait.messageTalk.message.toString());
    views.setViewVisibility(R.id.baloon0, ImageView.VISIBLE);
    WidgetManager.getInstance().miku.currentSurface = MikuHatsune.SURFACE_ANGRY;
  }

}
