package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ScheduledSmsList extends Activity {
	private static final String TAG = ScheduledSmsList.class.getSimpleName();

	private ListView smsListView;

	private SmsListAdapter smsListAdapter;

	private SimpleCursorAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		smsListView = (ListView) findViewById(R.id.smsList);
		Cursor c = managedQuery(SmsMessage.CONTENT_URI, null, null, null, null);
		String[] valuePosition = {SmsMessage.RECEIVER, SmsMessage.MESSAGE};
		int[] uiPosition = {R.id.numberText, R.id.messageText};
		adapter = new SimpleCursorAdapter(this,R.layout.list_item,c, valuePosition, uiPosition);
		smsListView.setAdapter(adapter);
		
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