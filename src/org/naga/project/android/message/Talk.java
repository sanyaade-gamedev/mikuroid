package org.naga.project.android.message;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.naga.project.android.mikuroid.widget.scene.Scene;

import android.os.Handler;
import android.os.Message;

public class Talk {

  public static final int TALKING = 1;
  public static final int SHOW_ALL = 2;
  public static final int NOTHING = 3;

  public Talk(Scene sc, long tspeed, int rindex) {
    this.scene = sc;
    this.talkSpeed = tspeed;
    this.returnIndex = rindex;

    this.messageQueue = new ConcurrentLinkedQueue<String>();
    this.message = new StringBuilder();
    this.talking = false;
    this.initTalk();
  }

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.talking = false;
    this.forceStop();
  }

  /**
   * Process talk.
   * 
   * @return Return true when process talk. Return false when nothing to talk.
   */
  public int execute() {
    // Finish speaking and show all message.
    if (this.talking) {
      this.talkHandler.sendEmptyMessage(Talk.HANDLE_SHOW_MESSAGE);
      return Talk.SHOW_ALL;
    }

    this.initTalk();

    this.currentMessage = this.messageQueue.poll();
    if (null == this.currentMessage) {
      this.currentMessage = "";
      // Nothing to talk.
      return Talk.NOTHING;
    }

    this.talking = true;

    this.talkHandler.sendEmptyMessage(Talk.HANDLE_TALK_MESSAGE);

    return Talk.TALKING;
  }

  public boolean hasNext() {
    if (this.messageQueue.isEmpty()) {
      return false;
    }

    return true;
  }

  public static final int HANDLE_TALK_MESSAGE = 1;
  public static final int HANDLE_SHOW_MESSAGE = 2;
  public static final int HANDLE_TALK_STOP = 3;
  public static final int HANDLE_TALK_FORCE_STOP = 4;

  private Handler talkHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case Talk.HANDLE_TALK_MESSAGE:
        processTalking();
        // Execute scene's view process.
        scene.onView();
        break;

      case Talk.HANDLE_SHOW_MESSAGE:
        // Stop taling and show all.
        showAll();
        break;

      case Talk.HANDLE_TALK_STOP:
        // Stop talking.
        talking = false;
        break;

      case Talk.HANDLE_TALK_FORCE_STOP:
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
      this.talkHandler.sendEmptyMessage(Talk.HANDLE_TALK_STOP);
      return;
    }
    // Return line.
    if (this.messageIndex % this.returnIndex == 0) {
      this.message.append("\n");
    }
    this.message.append(this.currentMessage.charAt(this.messageIndex));
    this.messageIndex++;

    this.talkHandler.sendEmptyMessageDelayed(Talk.HANDLE_TALK_MESSAGE,
        talkSpeed);
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
    this.talking = false;
    this.talkHandler.removeMessages(Talk.HANDLE_TALK_MESSAGE);
    this.talkHandler.removeMessages(Talk.HANDLE_TALK_STOP);
    this.messageQueue.clear();
  }

  private Scene scene;

  /**
   * Thread-safe message queue.
   */
  public ConcurrentLinkedQueue<String> messageQueue;

  /**
   * Message to show display.
   */
  public StringBuilder message;

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

}
