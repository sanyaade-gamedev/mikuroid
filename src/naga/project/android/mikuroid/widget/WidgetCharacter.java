package naga.project.android.mikuroid.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

/**
 * Widget character.
 * 
 * @author reciente
 *
 */
public class WidgetCharacter {
    
    public void saveMessage(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs =
            context.getSharedPreferences(this.prefName, Context.MODE_PRIVATE).edit();
        prefs.putString(this.prefName + appWidgetId, text);
        prefs.commit();
    }

    WidgetCharacter(String name) {
        this.prefName = name;
    }

    private String prefName;

    /** To edit text message. */
    private EditText editText;

}
