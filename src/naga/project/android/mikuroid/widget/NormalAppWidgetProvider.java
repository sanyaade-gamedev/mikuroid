package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.MikuroidActivity;
import naga.project.android.mikuroid.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class NormalAppWidgetProvider extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManger, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		
		// Perform this loop procedure for each App Widget that belongs to this provider.
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			
			// Create an Intent to launch Activity.
			Intent intent = new Intent(context, MikuroidActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			// Get the layout for the App Widget and attach an on-click listener to the button.
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.normal_appwidget);
			views.setOnClickPendingIntent(R.id.widget, pendingIntent);
			
			// Tell the AppWidgetmanager to perform an update on the current App Widget
			appWidgetManger.updateAppWidget(appWidgetId, views);
		}

	}

}
