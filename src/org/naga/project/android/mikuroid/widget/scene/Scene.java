package org.naga.project.android.mikuroid.widget.scene;

import android.content.Intent;

public interface Scene {

  public abstract boolean create();

  public abstract void onUpdate(Intent intent);

  public abstract void onView();

}
