package org.naga.project.nicovideo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class NicovideoNetwork {

  private static final String DAILY_RANKING_VOCALOID = "http://www.nicovideo.jp/ranking/fav/daily/vocaloid?rss=atom";

  public static List<NicovideoEntry> requestDailyRankingVOCALOID() {
    final HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(NicovideoNetwork.DAILY_RANKING_VOCALOID);

    HttpResponse httpResponse = null;
    try {
      // HTTP request.
      httpResponse = httpClient.execute(httpGet);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      httpResponse = null;
    } catch (IOException e) {
      e.printStackTrace();
      httpResponse = null;
    }

    // Has error?
    if (null == httpResponse) {
      return null;
    }

    Log.d("NicovideoRequest", "HTTP STATUS : "
        + httpResponse.getStatusLine().getStatusCode());

    // Check HTTP response status.
    if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
      return null;
    }

    InputStream is = null;
    List<NicovideoEntry> entryList = null;
    try {
      is = httpResponse.getEntity().getContent();
      entryList = NicovideoLoader.LoadEntryFromFeed(is);

      is.close();
      is = null;
    } catch (IllegalStateException e) {
      e.printStackTrace();
      entryList = null;
    } catch (IOException e) {
      e.printStackTrace();
      entryList = null;
    } finally {
      if (null != is) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        is = null;
      }
    }

    return entryList;
  }

}
