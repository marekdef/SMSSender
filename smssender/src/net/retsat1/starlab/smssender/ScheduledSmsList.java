package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.ui.adapter.SmsListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
<<<<<<< HEAD:smssender/src/net/retsat1/starlab/smssender/ScheduledSmsList.java

	private static final String[] COUNTRIES = new String[]{"Belgium", "France",
			"Italy", "Germany", "Spain"};

	private void setAdapterForNumberEditor() {
		ContentResolver content = getContentResolver();
		String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
		String[] PROJECTION = new String[]{

		ContactsContract.Contacts._ID,
		ContactsContract.Contacts.DISPLAY_NAME,

		ContactsContract.Contacts.HAS_PHONE_NUMBER,

		};

		Cursor cursor = content.query(ContactsContract.Contacts.CONTENT_URI,
				PROJECTION, SELECTION, null, null);
		while (cursor.moveToNext()) {
			String data = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			int hasNumber = cursor
					.getInt(cursor
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

			Log.d(TAG, "data " + data + " hasNumber=" + hasNumber);
		}

		if (cursor == null) {
			// TODO
			Log.e(TAG, "No contacts to display");
		} else {
			String[] columns = new String[]{
					ContactsContract.Contacts.DISPLAY_NAME,
					ContactsContract.Contacts.HAS_PHONE_NUMBER};
			int[] names = new int[]{R.id.row_display_name,
					R.id.row_phone_number};

			SimpleCursorAdapter adapterPhone = new SimpleCursorAdapter(this,
					R.layout.phone_row_entry, cursor, columns, names);
			startManagingCursor(cursor);
			numberEditText.setAdapter(adapterPhone);

		}

=======
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
>>>>>>> d85c6151beec1d07f442f4320fe5030ce0a9ba8a:smssender/src/main/java/net/retsat1/starlab/smssender/ScheduledSmsList.java
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