package naga.project.android.mikuroid.widget;

import android.view.View;

public class OnClickListener implements View.OnClickListener {

    public OnClickListener(WidgetConfigure wc) {
        super();
        
        this.widgetConfigure = wc;
    }

    public void onClick(View v) {
        // When the button is clicked, save the string in out prefs and return
        // that they
        // clicked OK.
    }

    private WidgetConfigure widgetConfigure;

}
