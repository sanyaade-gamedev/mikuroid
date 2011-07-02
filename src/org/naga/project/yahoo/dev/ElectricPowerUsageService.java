package org.naga.project.yahoo.dev;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.naga.project.android.network.NetworkManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class ElectricPowerUsageService {

  public static final String API_URL = "http://setsuden.yahooapis.jp/v1/Setsuden/latestPowerUsage";

  public static final String API = "PFmZutyxg67OQHwCvFrgTRWu5jpcxcjRinDc.HNBPf.El13gdfWLKImGq6TOLgbY1AI-";

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String tokyo = "tokyo";
  public static final String tohoku = "tohoku";
  public static final String kansai = "kansai";

  public ElectricPowerUsage httpRequest() {
    String request_url = ElectricPowerUsageService.API_URL + "?appid="
        + ElectricPowerUsageService.API;
    // TODO Set request parameters

    InputStream is = NetworkManager.getInstance().httpRequest(request_url);

    ElectricPowerUsage electricPowerUsage = this.load(is);

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

  private ElectricPowerUsage load(InputStream is) {
    ElectricPowerUsage electricPowerUsage = new ElectricPowerUsage();

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

          if ("Area".equals(name)) {
            electricPowerUsage.area = parser.nextText();
          } else if ("Usage".equals(name)) {
            electricPowerUsage.usage = new Integer(parser.nextText());
          } else if ("Capacity".equals(name)) {
            electricPowerUsage.capacity = new Integer(parser.nextText());
          } else if ("Date".equals(name)) {
            try {
              electricPowerUsage.date = simpleDateFormat.parse(parser
                  .nextText());
            } catch (ParseException e) {
              e.printStackTrace();
              electricPowerUsage.date = null;
            }
          } else if ("Hour".equals(name)) {
            electricPowerUsage.hour = new Integer(parser.nextText());
          }
        }
      }
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      electricPowerUsage = null;
    } catch (IOException e) {
      e.printStackTrace();
      electricPowerUsage = null;
    }

    return electricPowerUsage;
  }

}
