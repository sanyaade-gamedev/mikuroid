package naga.project.android.nicovideo;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

abstract public class NicovideoLoader {

  private static String ENCODING = "UTF-8";

  private static final String DATE_FORMAT = "yyyy--MMdd-HH:mm:ss+09:00";

  public static List<NicovideoEntry> LoadEntryFromFeed(InputStream is) {
    XmlPullParser parser = Xml.newPullParser();
    try {
      parser.setInput(is, NicovideoLoader.ENCODING);
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      return null;
    }

    List<NicovideoEntry> entryList = new ArrayList<NicovideoEntry>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        NicovideoLoader.DATE_FORMAT);

    int eventType;
    try {
      eventType = parser.getEventType();
      NicovideoEntry entry = null;
      while (eventType != XmlPullParser.END_DOCUMENT) {
        String name = null;

        if (eventType == XmlPullParser.START_TAG) {
          name = parser.getName();

          if ("entry".equals(name)) {
            // New entry. <entry>
            entry = new NicovideoEntry();
          }

          if (null != entry) {
            if ("title".equals(name)) {
              entry.setTitle(parser.nextText());
              Log.d("title", entry.getTitle());
            } else if ("link".equals(name)) {
              entry.setLink(parser.getAttributeValue(null, "href"));
              Log.d("link", entry.getLink());
            } else if ("id".equals(name)) {
              entry.setId(parser.nextText());
              Log.d("id", entry.getId());
            } else if ("published".equals(name)) {
              Date date = null;
              try {
                date = simpleDateFormat.parse(parser.nextText());
              } catch (ParseException e) {
                e.printStackTrace();
                date = null;
              }
              entry.setPublished(date);
            } else if ("updated".equals(name)) {
              Date date = null;
              try {
                date = simpleDateFormat.parse(parser.nextText());
              } catch (ParseException e) {
                e.printStackTrace();
                date = null;
              }
              entry.setUpdated(date);
            } else if ("content".equals(name)) {
              entry.setContent(parser.nextText());
            }
          }
        } else if (eventType == XmlPullParser.END_TAG) {
          name = parser.getName();
          if ("entry".equals(name)) {
            // Find </entry>
            if (null != entry) {
              entryList.add(entry);
            }
          }
        }

        eventType = parser.next();
      }
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return entryList;
  }

}
