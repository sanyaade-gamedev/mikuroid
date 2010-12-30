package naga.project.android.nicovideo;

import java.util.Date;

public class Entry {

  private String title;

  private String link;

  private String Id;

  private Date published;

  private Date updated;

  private String contentHTML;

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

  public String getContentHTML() {
    return contentHTML;
  }

  public void setContentHTML(String contentHTML) {
    this.contentHTML = contentHTML;
  }

}
