package naga.project.android.network;

import java.util.ArrayList;
import java.util.List;

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
