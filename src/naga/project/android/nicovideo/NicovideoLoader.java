package naga.project.android.nicovideo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

abstract public class NicovideoLoader {

  private static String ENCODING = "UTF-8";

  public static List<NicovideoEntry> LoadEntryFromFeed(InputStream is) {
    XmlPullParser parser = Xml.newPullParser();
    try {
      parser.setInput(is, NicovideoLoader.ENCODING);
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }

    List<NicovideoEntry> entryList = new ArrayList<NicovideoEntry>();

    return entryList;
  }

}
