package org.naga.project.android.mikuroid.widget.scene;

public abstract class Scene {

  public Scene(Scene sc) {
    super();

    this.finish = false;
    this.scene = sc;
  }

  public abstract boolean create();

  public void onUpdate() {
    if (null != this.scene) {
      // Child update.
      this.scene.onUpdate();
    } else {
      // My update.
      this.onUpdateProcess();
    }
  }

  public void onView() {
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

  protected boolean finish;

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

}
