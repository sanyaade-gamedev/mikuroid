package org.naga.project.android.mikuroid;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

public class MikuroidConfigureActivity extends Activity {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.configure);

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      this.appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);
    }
  }

  private int appWidgetId;

}
