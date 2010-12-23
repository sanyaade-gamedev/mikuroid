package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.widget.EditText;

public class MikuroidWidgetConfigure extends Activity {

	static final String TAG = "MikuroidWidgetConfigure";

	int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	EditText appWidgetPrefix;

	public MikuroidWidgetConfigure() {
		super();
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		// Set the result to CANCELED. This will cause the widget host to cancel
		// out of the widget placement if they press the back button.
		setResult(Activity.RESULT_CANCELED);
		
		// Bind the action for the save button.
		this.findViewById(R.id.widget);
	}

}
