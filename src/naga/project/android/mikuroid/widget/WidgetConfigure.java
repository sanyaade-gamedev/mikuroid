package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

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
        
        this.appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.d(WidgetConfigure.TAG, "onCreate()");

        // Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(Activity.RESULT_CANCELED);
        
        // Set the view layout resource to use.
        this.setContentView(R.layout.widget_configure);

        // Bind the action for the save button.
        Log.d(WidgetConfigure.TAG, "add onClickListener");
        this.findViewById(R.drawable.miku2).setOnClickListener(this.widgetClickListener);
        
        MikuHatsune.getInstance().create((EditText)this.findViewById(R.id.miku_edittext));
    }

    View.OnClickListener widgetClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            // Miku has clicked.
            MikuHatsune.getInstance().click(v, WidgetConfigure.this, appWidgetId);

            // Set views to context.
            Context context = WidgetConfigure.this;
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_message);
            views.setTextViewText(R.id.miku_message, "みっくみっく♪");
            
            // Create an Intent to launch Activity.
            Intent intent = new Intent(context, WidgetConfigure.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.putExtras(bundle);
            // Update current activity.
            PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            
            // Get the layout for the App Widget and attach an on-click listener to the views.
            views.setOnClickPendingIntent(R.id.miku, pendingIntent);

            AppWidgetManager.getInstance(context)
                .updateAppWidget(appWidgetId, views);
            
            // Make sure we pass back the original appWidgetId.
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // Close activity.
            setResult(Activity.RESULT_OK, resultValue);
            finish();
        }

    };
    
    private int appWidgetId;

}
