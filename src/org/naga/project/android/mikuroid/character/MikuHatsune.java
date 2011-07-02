package org.naga.project.android.mikuroid.character;

import android.util.Log;

import org.naga.project.android.mikuroid.R;

public class MikuHatsune {

  public static final int SURFACE_NORMAL = R.drawable.mikuroid001;
  public static final int SURFACE_ANGRY = R.drawable.mikuroid001;
  public static final int SURFACE_SURPRISED = R.drawable.mikuroid001;

  public static final int ID_IMAGE_VIEW = R.id.miku;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");
  }

  public boolean create() {
    Log.d("MikuHatsune", "create()");

    return true;
  }

  public int currentSurface = MikuHatsune.SURFACE_NORMAL;

}
