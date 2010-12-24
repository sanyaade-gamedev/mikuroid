package naga.project.android.mikuroid.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Widget character.
 * 
 * @author reciente
 * 
 */
public class WidgetCharacter {

    private static final String TAG = "WidgetCharacter";

    WidgetCharacter(String name) {
        this.prefName = name;
    }

    public void create(EditText et) {
        Log.d(WidgetCharacter.TAG, "create()");
        this.editText = et;

        this.editText.setText("はじめましてっ！みくだよっ！！");
    }

    public void click(View v, Context context, int appWidgetId) {
        // String message = this.editText.getText().toString();
        Log.d(WidgetCharacter.TAG, "click()");
        this.saveMessage(context, appWidgetId, "みくだよっ！");
    }

    private void saveMessage(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(
                this.prefName, Context.MODE_PRIVATE).edit();
        prefs.putString(this.prefName + appWidgetId, text);
        prefs.commit();
    }

    /** Preference name. */
    private String prefName;

    /** To edit text message. */
    private EditText editText;

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

}
