package org.naga.project.android.message;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Handler;
import android.os.Message;

public class Talk {

  public Talk(TalkView tv, long tspeed, int rindex) {
    this.talkView = tv;
    this.talkSpeed = tspeed;
    this.returnIndex = rindex;

    this.messageQueue = new ConcurrentLinkedQueue<String>();
    this.message = new StringBuilder();
    this.talking = false;
    this.initTalk();
  }

  private TalkView talkView;

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.talking = false;
    this.forceStop();
  }

  public static final int TALK_MESSAGE = 1;
  public static final int SHOW_MESSAGE = 2;
  public static final int TALK_STOP = 3;
  public static final int TALK_FORCE_STOP = 4;

  private Handler talkHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case Talk.TALK_MESSAGE:
        processTalking();
        // View talking message.
        talkView.view();
        break;

      case Talk.SHOW_MESSAGE:
        // Stop taling and show all.
        showAll();
        break;

      case Talk.TALK_STOP:
        // Stop talking.
        talking = false;
        break;

      case Talk.TALK_FORCE_STOP:
        // Force stop talking.
        talking = false;
        forceStop();
        break;
      }
    }

  };

  private void initTalk() {
    this.messageIndex = 0;
    this.currentMessage = "";
    this.message.setLength(0);
  }

  private void processTalking() {
    if (this.messageIndex >= this.currentMessage.length()) {
      this.talkHandler.sendEmptyMessage(Talk.TALK_STOP);
      return;
    }
    // Return line.
    if (this.messageIndex % this.returnIndex == 0) {
      this.message.append("\n");
    }
    this.message.append(this.currentMessage.charAt(this.messageIndex));
    this.messageIndex++;

    this.talkHandler.sendEmptyMessageDelayed(Talk.TALK_MESSAGE, talkSpeed);
  }

  /**
   * Process talk.
   * 
   * @return Return true when process talk. Return false when nothing to talk.
   */
  public boolean process() {
    // Finish speaking and show all message.
    if (this.talking) {
      this.talkHandler.sendEmptyMessage(Talk.SHOW_MESSAGE);
      return true;
    }

    this.initTalk();

    this.currentMessage = this.messageQueue.poll();
    if (null == this.currentMessage) {
      this.currentMessage = "";
      // Nothing to talk.
      return false;
    }

    this.talking = true;

    this.talkHandler.sendEmptyMessage(Talk.TALK_MESSAGE);

    return true;
  }

  /**
   * Stop taling and show message.
   */
  private void showAll() {
    this.talking = false;

    // Add a remaining characters.
    while (this.messageIndex < this.currentMessage.length()) {
      // Return line.
      if (this.messageIndex % this.returnIndex == 0) {
        this.message.append("\n");
      }
      this.message.append(this.currentMessage.charAt(this.messageIndex));
      this.messageIndex++;
    }
  }

  /**
   * Stop taling and clear data.
   */
  private void forceStop() {
    talking = false;
    this.talkHandler.removeMessages(Talk.TALK_MESSAGE);
    this.talkHandler.removeMessages(Talk.TALK_STOP);
    this.messageQueue.clear();
  }

  /**
   * Thread-safe message queue.
   */
  private ConcurrentLinkedQueue<String> messageQueue;

  public ConcurrentLinkedQueue<String> getMessageQueue() {
    return messageQueue;
  }

  /**
   * Message to show display.
   */
  private StringBuilder message;

  /**
   * True when character is talking.
   */
  private boolean talking;

  /**
   * Use to talk. Index of currentMessage. To show message step by step.
   */
  private int messageIndex;

  /**
   * Current message that character is talking.
   */
  private String currentMessage;

  /**
   * Talking speed. milliseconds
   */
  private long talkSpeed;

  /**
   * Number of line return index.
   */
  private int returnIndex;

  public StringBuilder getMessage() {
    return message;
  }

}
