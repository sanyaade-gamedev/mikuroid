package naga.project.android.nicovideo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

abstract public class NicovideoLoader {

  private List<NicovideoEntry> LoadEntryFromFeed(String xml) {
    List<NicovideoEntry> entryList = new ArrayList<NicovideoEntry>();

    XmlPullParser parser = Xml.newPullParser();
    try {
      parser.setInput(new StringReader(xml));
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }

    return entryList;
  }

}
