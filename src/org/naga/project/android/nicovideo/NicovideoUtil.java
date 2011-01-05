package org.naga.project.android.nicovideo;

import java.util.List;
import java.util.StringTokenizer;

import org.naga.project.android.network.NetworkNicovideo;

public abstract class NicovideoUtil {

  public static final Integer getId(NicovideoEntry entry) {
    if (null == entry || null == entry.getId()) {
      return null;
    }

    // Example of ID.
    // tag:nicovideo.jp,2010-12-26:/watch/sm13136668
    // Get the 2rd token.
    StringTokenizer token = new StringTokenizer(entry.getId(), "/");

    token.nextToken();
    token.nextToken();

    String buff = token.nextToken();

    // sm13136668
    // String prefix will sm or nm so delete 2 characters.

    Integer id = new Integer(buff.substring(2));

    return id;
  }

  public static String[] generateThumbnailUrls(List<NicovideoEntry> entryList) {
    String[] urls = new String[entryList.size()];

    for (int i = 0; i < entryList.size(); ++i) {
      Integer id = NicovideoUtil.getId(entryList.get(i));
      if (null == id) {
        continue;
      }

      urls[i] = "http://" + NetworkNicovideo.IMAGE_HOST + "/smile?i=" + id;
    }

    return urls;
  }

}
