package org.naga.project.nicovideo;

import java.util.Date;

public class NicovideoEntry {

  /**
   * Entry title.
   */
  private String title;

  /**
   * Link to entry.
   */
  private String link;

  /**
   * Entry ID.
   */
  private String Id;

  /**
   * Published date.
   */
  private Date published;

  /**
   * Updated date.
   */
  private Date updated;

  /**
   * Content by HTML.
   */
  private String content;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public Date getPublished() {
    return published;
  }

  public void setPublished(Date published) {
    this.published = published;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
