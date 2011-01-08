package org.naga.project.android.mikuroid.widget.scene;

import android.util.Log;

public abstract class Scene {

  public Scene(Scene sc) {
    super();

    this.finish = false;
    this.scene = sc;
  }

  public abstract boolean create();

  public void onUpdate() {
    Log.d("Scene", "onUpdate");
    if (null != this.scene) {
      // Child update.
      this.scene.onUpdate();
    } else {
      // My update.
      this.onUpdateProcess();
    }
  }

  public void onView() {
    Log.d("Scene", "onView");

    if (null != this.scene) {
      // Child view.
      this.scene.onView();

      // Child scene is finished?
      if (this.scene.finish) {
        this.scene = null;
      }
    } else {
      // My view.
      this.onViewProcess();
    }
  }

  protected abstract void onUpdateProcess();

  protected abstract void onViewProcess();

  private Scene scene;

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  protected boolean finish;

}
