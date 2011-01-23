package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.mikuroid.widget.action.Action;

import android.content.Intent;

public interface Scene {

  public boolean create();

  public void onUpdate(Intent intent);

  public void onView();

  public void setReserveAction(Action action);

}
