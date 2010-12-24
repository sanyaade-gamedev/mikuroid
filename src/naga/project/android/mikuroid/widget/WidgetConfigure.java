package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * The configuration screen for the WidgetProvider widget.
 * 
 * @author reciente
 * 
 */
public class WidgetConfigure extends Activity {

    private static final String TAG = "WidgetConfigure";

    public WidgetConfigure() {
        super();
        
        this.miku = new WidgetCharacter("naga.project.android.WidgetCharacter.MikuHatsune");
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        Log.d(WidgetConfigure.TAG, "onCreate()");

        // Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(Activity.RESULT_CANCELED);

        // Bind the action for the save button.
        this.findViewById(R.id.widget);
        
        this.miku.create((EditText)this.findViewById(R.id.miku_edittext));
    }
    
    View.OnClickListener mikuClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            // Miku has clicked.
            // Use AppWidgetManager.INVALID_APPWIDGET_ID to share edit message with any widgets.
            miku.click(v, WidgetConfigure.this, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

    };
    
    /** Widget character Miku Hatsune. */
    private WidgetCharacter miku;

}
