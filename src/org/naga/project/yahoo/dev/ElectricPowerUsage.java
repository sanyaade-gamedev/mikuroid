package org.naga.project.yahoo.dev;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * Entity class for Yahoo Developmentwork ElectricPowerUsage
 * 
 * @author reciente
 * 
 */
public class ElectricPowerUsage {

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public String area;

  public Integer usage;

  public Integer capacity;

  public Date date;

  public Integer hour;

  public void Load(InputStream is) {
    XmlPullParser parser = Xml.newPullParser();
    try {
      parser.setInput(is, "UTF-8");
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      return;
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        ElectricPowerUsage.DATE_FORMAT);

    try {
      int eventType = parser.getEventType();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String name = null;

        if (eventType == XmlPullParser.START_TAG) {
          // New entry. <ElectricPowerUsage>
          name = parser.getName();

          if ("Area".equals(name)) {
            this.area = parser.nextText();
          } else if ("Usage".equals(name)) {
            this.usage = new Integer(parser.nextText());
          } else if ("Capacity".equals(name)) {
            this.capacity = new Integer(parser.nextText());
          } else if ("Date".equals(name)) {
            try {
              this.date = simpleDateFormat.parse(parser.nextText());
            } catch (ParseException e) {
              e.printStackTrace();
              this.date = null;
            }
          } else if ("Hour".equals(name)) {
            this.hour = new Integer(parser.nextText());
          }
        }
      }
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
