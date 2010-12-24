package naga.project.android.mikuroid.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Provide miku widget.
 * 
 * @author reciente
 * 
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManger, int[] appWidgetIds) {
        Log.d(WidgetProvider.TAG, "onUpdate()");
        context.startService(new Intent(context, WidgetUpdateService.class));
        
        // onUpdate must be called at first and only once.
        if (null == this.miku) {
            this.miku = new WidgetCharacter("naga.project.android.WidgetCharacter.MikuHatsune");
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(WidgetProvider.TAG, "onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(WidgetProvider.TAG, "onDisabled");
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        Log.d(WidgetProvider.TAG, "onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(WidgetProvider.TAG, "onReceive");
        super.onReceive(context, intent);
        
        // Stop service when discard widget from home.
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(intent.getAction())) {
            Log.d(WidgetProvider.TAG, "stop service");
            context.stopService(new Intent(context, WidgetUpdateService.class));
        }
    }
    
    /** Widget character Miku Hatsune. */
    private WidgetCharacter miku;

}
