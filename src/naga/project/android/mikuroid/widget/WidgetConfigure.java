package naga.project.android.mikuroid.widget;

import naga.project.android.mikuroid.R;
import android.app.Activity;
import android.os.Bundle;

public class WidgetConfigure extends Activity {

	static final String TAG = "MikuroidWidgetConfigure";

	public WidgetConfigure() {
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
