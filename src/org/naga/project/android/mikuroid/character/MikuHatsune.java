package org.naga.project.android.mikuroid.character;

import android.util.Log;

import org.naga.project.android.mikuroid.R;

public class MikuHatsune {

  public static final int SURFACE_NORMAL = R.drawable.mikuroid001;
  public static final int SURFACE_ANGRY = R.drawable.mikuroid002;
  public static final int SURFACE_SURPRISED = R.drawable.mikuroid003;

  public static final int ID_IMAGE_VIEW = R.id.miku;

  public MikuHatsune() {
    super();
    Log.d("MikuHatsune", "constructor");

    this.activeSurface = MikuHatsune.SURFACE_NORMAL;
  }

  public boolean create() {
    Log.d("MikuHatsune", "create()");

    /*
     * List<NicovideoEntry> entryList = NicovideoNetwork
     * .requestDailyRankingVOCALOID(); this.nicoEntryQueue.addAll(entryList);
     * 
     * new DownloadImageTask().execute(NicovideoUtil
     * .generateThumbnailUrls(entryList));
     */

    return true;
  }

  /*
   * Bitmap bitmap = NetworkManager.getInstance().load(
   * "http://tn-skr2.smilevideo.jp/smile?i=13136668");
   * 
   * if (null != bitmap) { views.setViewVisibility(R.id.nicovideo_image,
   * ImageView.VISIBLE); views.setImageViewBitmap(R.id.nicovideo_image, bitmap);
   * }
   */

  public int activeSurface;

}
