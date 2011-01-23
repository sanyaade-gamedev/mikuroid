package org.naga.project.android.mikuroid.widget.scene;

import org.naga.project.android.message.MessageTalk;

import android.content.Intent;

public abstract class Scene {

  public Scene(Scene sc) {
    super();

    this.finish = false;
    this.scene = sc;
  }

  public abstract boolean create();

  public abstract void onUpdate(Intent intent);

  public abstract void onView();

  private Scene scene;

  protected boolean finish;

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  public abstract MessageTalk getMessageTalk();

}
