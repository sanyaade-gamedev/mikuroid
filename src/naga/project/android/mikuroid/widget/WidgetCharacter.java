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
    this.speaking = false;
    this.initSpeak();
  }

  public void Destroy() {
    this.message.setLength(0);
    this.messageQueue.clear();
    this.speaking = false;
    this.forceStop();
  }

  static final int SPEAK_MESSAGE = 1;
  static final int SPEAK_STOP = 2;

  private Handler handler = new Handler() {
    private static final String TAG = "WidgetCharacter.Handler";

    @Override
    public void handleMessage(Message msg) {
      Log.d(TAG, "handlerMessage");
      switch (msg.what) {
      case WidgetCharacter.SPEAK_MESSAGE:
        processSpeak();
        
        break;

      case WidgetCharacter.SPEAK_STOP:
        // Stop speaking.
        synchronized (speaking) {
          speaking = false;
        }
        
        break;
      }
    }
  };

  private void initSpeak() {
    this.messageIndex = 0;
    this.currentMessage = "";
  }

  private void processSpeak() {
    if (this.messageIndex >= this.currentMessage.length()) {
      this.handler.sendEmptyMessage(WidgetCharacter.SPEAK_STOP);
      return;
    }

    this.messageIndex++;
    int index = messageIndex;
    if (index < 0) {
      index = 0;
    }
    this.message.append(this.currentMessage.charAt(index));
    
    this.handler.sendEmptyMessageDelayed(WidgetCharacter.SPEAK_MESSAGE, talkSpeed);
  }

  public synchronized void play() {
    // TODO Finish speaking and show all message.
    if (this.speaking) {
      return;
    }

    this.currentMessage = this.messageQueue.poll();
    if (null == this.currentMessage) {
      this.currentMessage = "";
      return;
    }

    this.initSpeak();

    this.handler.sendEmptyMessage(WidgetCharacter.SPEAK_MESSAGE);
  }

  protected void forceStop() {
    this.handler.removeMessages(WidgetCharacter.SPEAK_MESSAGE);
    this.handler.removeMessages(WidgetCharacter.SPEAK_STOP);
    this.messageQueue.clear();
  }

  protected ConcurrentLinkedQueue<String> messageQueue;

  private StringBuilder message;

  private int messageIndex;

  protected Boolean speaking;

  protected String currentMessage;

  /** Talking speed. milliseconds */
  private int talkSpeed = 30;

}
