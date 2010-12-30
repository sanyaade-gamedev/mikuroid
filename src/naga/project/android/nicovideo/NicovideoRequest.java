package naga.project.android.nicovideo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
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
      httpResponse = httpClient.execute(httpGet);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      httpResponse = null;
    } catch (IOException e) {
      e.printStackTrace();
      httpResponse = null;
    }

    if (null == httpResponse) {
      return null;
    }

    Log.d("NicovideoRequest",
        Integer.toString(httpResponse.getStatusLine().getStatusCode()));

    if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
      HttpEntity entry = httpResponse.getEntity();
      try {
        final InputStream is = entry.getContent();
        String content = is.toString();
        is.close();
      } catch (IllegalStateException e) {
        e.printStackTrace();
        return null;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    return null;
  }

}
