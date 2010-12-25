package naga.project.android.mikuroid.widget;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    this.talking = false;
    this.initTalk();
  }

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.talking = false;
    this.forceStop();
  }

  static final int SPEAK_MESSAGE = 1;
  static final int SPEAK_STOP = 2;

  private Handler talkHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case WidgetCharacter.SPEAK_MESSAGE:
        processTalk();
        
        break;

      case WidgetCharacter.SPEAK_STOP:
        // Stop speaking.
        synchronized (talking) {
          talking = false;
        }
        
        break;
      }
    }

  };

  private void initTalk() {
    this.messageIndex = 0;
    this.currentMessage = "";
  }

  private void processTalk() {
    this.messageIndex++;
    int index = this.messageIndex;
    if (index < 0) {
      index = 0;
    }
    if (index >= this.currentMessage.length()) {
      this.talkHandler.sendEmptyMessage(WidgetCharacter.SPEAK_STOP);
      return;
    }
    this.message.append(this.currentMessage.charAt(index));
    
    this.talkHandler.sendEmptyMessageDelayed(WidgetCharacter.SPEAK_MESSAGE, talkSpeed);
  }

  public synchronized void play() {
    Log.d("play", "messageQueue.size() : " + this.messageQueue.size());
    // TODO Finish speaking and show all message.
    if (this.talking) {
      return;
    }
    
    this.initTalk();

    this.currentMessage = this.messageQueue.poll();
    if (null == this.currentMessage) {
      this.currentMessage = "";
      return;
    }

    this.talkHandler.sendEmptyMessage(WidgetCharacter.SPEAK_MESSAGE);
  }

  protected void forceStop() {
    this.talkHandler.removeMessages(WidgetCharacter.SPEAK_MESSAGE);
    this.talkHandler.removeMessages(WidgetCharacter.SPEAK_STOP);
    this.messageQueue.clear();
  }

  protected ConcurrentLinkedQueue<String> messageQueue;

  private StringBuilder message;

  private int messageIndex;

  protected Boolean talking;

  protected String currentMessage;

  /** Talking speed. milliseconds */
  private int talkSpeed = 1000;

}
