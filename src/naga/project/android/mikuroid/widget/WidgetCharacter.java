package naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Widget character.
 * 
 * @author reciente
 * 
 */
public class WidgetCharacter {

  public WidgetCharacter() {
    this.message = new StringBuilder();
    this.messageQueue = new ConcurrentLinkedQueue<String>();
    this.speaking = false;
  }

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.speaking = false;
  }

  public void processTalk() {
  }

  protected ConcurrentLinkedQueue<String> messageQueue;

  protected StringBuilder message;

  protected boolean speaking;

  private String currentMessage;

}
