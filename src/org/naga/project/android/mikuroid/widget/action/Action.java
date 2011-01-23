package org.naga.project.android.mikuroid.widget.action;

import org.naga.project.android.mikuroid.widget.scene.Scene;

import android.content.Intent;
import android.widget.RemoteViews;

abstract public class Action {

  public Action(Scene s) {
    this.scene = s;
  }

  public abstract boolean create();

  public abstract boolean update(Intent intent);

  public abstract void view(RemoteViews views);

  protected Scene scene;

}
