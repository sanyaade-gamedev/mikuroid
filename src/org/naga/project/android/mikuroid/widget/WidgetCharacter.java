package org.naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.naga.project.android.nicovideo.NicovideoEntry;

import android.os.Handler;
import android.os.Message;

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
    this.nicoEntryQueue = new ConcurrentLinkedQueue<NicovideoEntry>();
    this.talking = false;
    this.initTalk();
  }

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.talking = false;
    this.forceStop();
  }

  static final int TALK_MESSAGE = 1;
  static final int TALK_STOP = 2;

  protected Handler talkHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case WidgetCharacter.TALK_MESSAGE:
        processTalk();
        // View talking message.
        WidgetManager.getInstance().view();
        break;

      case WidgetCharacter.TALK_STOP:
        // Stop talking.
        synchronized (talking) {
          talking = false;
        }

        break;
      }
    }

  };

  protected void initTalk() {
    this.messageIndex = 0;
    this.currentMessage = "";
    this.message.setLength(0);
  }

  private void processTalk() {
    if (this.messageIndex >= this.currentMessage.length()) {
      this.talkHandler.sendEmptyMessage(WidgetCharacter.TALK_STOP);
      return;
    }
    // Return line.
    if (this.messageIndex % 12 == 0) {
      this.message.append("\n");
    }
    this.message.append(this.currentMessage.charAt(this.messageIndex));
    this.messageIndex++;

    this.talkHandler.sendEmptyMessageDelayed(WidgetCharacter.TALK_MESSAGE,
        talkSpeed);
  }

  protected void forceStop() {
    this.talkHandler.removeMessages(WidgetCharacter.TALK_MESSAGE);
    this.talkHandler.removeMessages(WidgetCharacter.TALK_STOP);
    this.messageQueue.clear();
  }

  protected ConcurrentLinkedQueue<String> messageQueue;

  protected ConcurrentLinkedQueue<NicovideoEntry> nicoEntryQueue;

  /**
   * Message to show display.
   */
  protected StringBuilder message;

  /**
   * True when character is talking.
   */
  protected Boolean talking;

  /**
   * Use to talk. Index of currentMessage. To show message step by step.
   */
  private int messageIndex;

  /**
   * Current message that character is talking.
   */
  protected String currentMessage;

  /**
   * Talking speed. milliseconds
   */
  private long talkSpeed = 100;

}
