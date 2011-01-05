package org.naga.project.android.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetworkManager {

  public static NetworkManager getInstance() {
    if (null == NetworkManager.instance) {
      NetworkManager.instance = new NetworkManager();
    }
    return NetworkManager.instance;
  }

  private NetworkManager() {
    super();
  }

  private static NetworkManager instance;

  public Bitmap load(String url) {
    HttpGet get = null;
    try {
      get = new HttpGet(new URL(url).toURI());
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }

    HttpClient client = new DefaultHttpClient();

    HttpResponse response = null;
    try {
      response = client.execute(get);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    HttpEntity entity = response.getEntity();

    BufferedHttpEntity bufferdEntity = null;
    try {
      bufferdEntity = new BufferedHttpEntity(entity);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    final long contentLength = bufferdEntity.getContentLength();

    Bitmap bitmap = null;
    if (contentLength >= 0) {
      InputStream is = null;
      try {
        is = bufferdEntity.getContent();
        bitmap = BitmapFactory.decodeStream(is);

        is.close();
        is = null;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
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
    }

    return bitmap;
  }

}
