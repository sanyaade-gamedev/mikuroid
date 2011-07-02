package org.naga.project.yahoo.dev;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.naga.project.android.network.NetworkManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class ElectricPowerUsageService {

  public static final String API_URL = "http://setsuden.yahooapis.jp/v1/Setsuden/latestPowerUsage";

  public static final String API = "PFmZutyxg67OQHwCvFrgTRWu5jpcxcjRinDc.HNBPf.El13gdfWLKImGq6TOLgbY1AI-";

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String TOKYO = "tokyo";
  public static final String TOHOKU = "tohoku";
  public static final String KANSAI = "kansai";

  /**
   * 
   * @return
   */
  public static ElectricPowerUsage httpRequest() {
    String request_url = ElectricPowerUsageService.API_URL + "?appid="
        + ElectricPowerUsageService.API;
    // TODO Set request parameters

    InputStream is = NetworkManager.getInstance().httpRequest(request_url);

    ElectricPowerUsage electricPowerUsage = ElectricPowerUsageService.load(is);

    try {
      is.close();
      is = null;
    } catch (IOException e) {
      e.printStackTrace();
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      is = null;
    }

    return electricPowerUsage;
  }

  /**
   * 
   * @param is
   * @return
   */
  private static ElectricPowerUsage load(InputStream is) {
    ElectricPowerUsage usage = new ElectricPowerUsage();

    XmlPullParser parser = Xml.newPullParser();
    try {
      parser.setInput(is, "UTF-8");
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      return null;
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        ElectricPowerUsageService.DATE_FORMAT);

    try {
      int eventType = parser.getEventType();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String name = null;

        if (eventType == XmlPullParser.START_TAG) {
          // New entry. <ElectricPowerUsage>
          name = parser.getName();
          
          Log.d("XML", name);

          if ("Area".equals(name)) {
            usage.area = parser.nextText();
          } else if ("Usage".equals(name)) {
            usage.usage = new Integer(parser.nextText());
            Log.d("XML", usage.usage.toString());
          } else if ("Capacity".equals(name)) {
            usage.capacity = new Integer(parser.nextText());
            Log.d("XML", usage.capacity.toString());
          } else if ("Date".equals(name)) {
            try {
              usage.date = simpleDateFormat.parse(parser.nextText());
            } catch (ParseException e) {
              e.printStackTrace();
              usage.date = null;
            }
          } else if ("Hour".equals(name)) {
            usage.hour = new Integer(parser.nextText());
          }
        }

        eventType = parser.next();
      }
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      usage = null;
    } catch (IOException e) {
      e.printStackTrace();
      usage = null;
    }

    return usage;
  }

}
