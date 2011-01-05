package org.naga.project.android.task;

import java.util.ArrayList;
import java.util.List;

import org.naga.project.android.network.NetworkManager;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageTask extends AsyncTask<String, Void, List<Bitmap>> {

  @Override
  protected List<Bitmap> doInBackground(String... urls) {
    if (null == urls) {
      return null;
    }

    List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    for (String url : urls) {
      Bitmap bitmap = NetworkManager.getInstance().load(url);
      if (null != bitmap) {
        bitmapList.add(bitmap);
        Log.d("DownloadImageTask$doInBackground", "downloaded");
      }
    }

    return bitmapList;
  }

}
