package org.naga.project.yahoo.dev;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Network {

  private static final String API_URL = "http://setsuden.yahooapis.jp/v1/Setsuden/latestPowerUsage";

  public static final String API = "PFmZutyxg67OQHwCvFrgTRWu5jpcxcjRinDc.HNBPf.El13gdfWLKImGq6TOLgbY1AI-";

  public static final String tokyo = "tokyo";
  public static final String tohoku = "tohoku";
  public static final String kansai = "kansai";

  public ElectricPowerUsage requestElectricPowerUsage() {
    String request_url = Network.API_URL + "?appid=" + Network.API;
    // TODO Set request parameters

    final HttpClient httpClient = new DefaultHttpClient();

    HttpGet httpGet = new HttpGet(request_url);

    HttpResponse httpResponse = null;
    try {
      // HTTP request
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

    Log.d("ElectricPowerUsage HTTP Request", "HTTP STATUS : "
        + httpResponse.getStatusLine().getStatusCode());

    // Check HTTP response status.
    if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
      return null;
    }

    InputStream is = null;
    ElectricPowerUsage electricPowerUsage = new ElectricPowerUsage();
    try {
      is = httpResponse.getEntity().getContent();
    } catch (IllegalStateException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    electricPowerUsage.Load(is);

    return electricPowerUsage;
  }
}
