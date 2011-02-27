package net.retsat1.starlab.smssender;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import net.retsat1.starlab.smssender.R;

public class ScheduledSmsList extends Activity {
    private static final String TAG = "ScheduledSmsList";

    Button button; 
    LinearLayout linearLayout;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.waitForDebugger();
        setContentView(R.layout.main);
        linearLayout = (LinearLayout) findViewById(R.id.topLayaout);
        Log.d(TAG, "wiadomość widdsdsfsfsfsfsfadomosć ");
        button = new Button(this);
        
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "click1");
				
				
				Log.d(TAG, "click2");
				Log.d(TAG, "click3");
			}
		});
        linearLayout.addView(button);
        
    }
}