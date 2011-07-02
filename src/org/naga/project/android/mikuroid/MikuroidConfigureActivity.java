package org.naga.project.android.mikuroid;

import org.naga.project.android.mikuroid.widget.WidgetManager;
import org.naga.project.yahoo.dev.ElectricPowerUsageInformation;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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

    final CheckBox chBoxTokyo = (CheckBox) findViewById(R.id.checkBoxTokyo);
    final CheckBox chBoxTohoku = (CheckBox) findViewById(R.id.checkBoxTohoku);
    final CheckBox chBoxkKansai = (CheckBox) findViewById(R.id.checkBoxKansai);

    ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;

    chBoxTokyo.setChecked(info.useTokyo);
    chBoxTohoku.setChecked(info.useTohoku);
    chBoxkKansai.setChecked(info.useKansai);

    // Set listener
    chBoxTokyo.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        CheckBox checkbox = (CheckBox) v;
        ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;
        info.useTokyo = checkbox.isChecked();
      }
    });
    chBoxTohoku.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        CheckBox checkbox = (CheckBox) v;
        ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;
        info.useTohoku = checkbox.isChecked();
      }
    });
    chBoxkKansai.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        CheckBox checkbox = (CheckBox) v;
        ElectricPowerUsageInformation info = WidgetManager.getInstance().epuInformation;
        info.useKansai = checkbox.isChecked();
      }
    });
  }

  private int appWidgetId;

}
