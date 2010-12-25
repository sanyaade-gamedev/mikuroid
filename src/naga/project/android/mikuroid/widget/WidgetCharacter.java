package naga.project.android.mikuroid.widget;

import android.util.Log;

/**
 * Widget character.
 * 
 * @author reciente
 * 
 */
public class WidgetCharacter {

    private static final String TAG = "WidgetCharacter";

    public WidgetCharacter() {
        this.message = new StringBuilder();
    }

    public void create() {
        Log.d(WidgetCharacter.TAG, "create()");
        this.message.setLength(0);
    }

    protected StringBuilder message;

}
