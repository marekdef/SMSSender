package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ScheduledSmsList extends Activity {
	private static final String TAG = ScheduledSmsList.class.getSimpleName();

	private ListView smsListView;

	private SmsListAdapter smsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list);
		
		smsListView = (ListView) findViewById(R.id.smsList);
//		smsListAdapter = new SmsListAdapter(this, R.layout.list_item);
//		smsListAdapter.add(new SmsMessage("ToNumber", "Message"));
//		smsListView.setAdapter(smsListAdapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case R.id.new_sms:
			startActivity(new Intent(this, ScheduleNewSms.class));
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}