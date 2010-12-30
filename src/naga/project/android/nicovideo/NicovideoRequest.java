package naga.project.android.nicovideo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class NicovideoRequest {

  private static String DAILY_RANKING_VOCALOID = "http://www.nicovideo.jp/ranking/fav/daily/vocaloid";

  public static List<NicovideoEntry> requestDailyRankingVOCALOID() {
    final HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(NicovideoRequest.DAILY_RANKING_VOCALOID);

    // Get HttpParams instance and set params.
    HttpParams httpParams = httpClient.getParams();
    httpParams.setParameter("res", "atom");

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

    Log.d("NicovideoRequest",
        Integer.toString(httpResponse.getStatusLine().getStatusCode()));

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
      }
    }

    return entryList;
  }

}
