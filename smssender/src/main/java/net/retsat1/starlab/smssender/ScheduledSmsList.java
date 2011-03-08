package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsListAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class ScheduledSmsList extends Activity implements OnClickListener {
	private static final String TAG = "ScheduledSmsList";

	private static final int REQUEST_CODE = 0;

	Button sendButton;

	private DatePicker dataPicker;

	private AutoCompleteTextView numberEditText;

	private EditText messageEditText;

	private ListView smsListView;

	private SmsListAdapter smsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		dataPicker = (DatePicker) findViewById(R.id.dataPicker);
		numberEditText = (AutoCompleteTextView) findViewById(R.id.numberEditText);
		messageEditText = (EditText) findViewById(R.id.messageEditText);
		sendButton = (Button) findViewById(R.id.send_button);
		sendButton.setOnClickListener(this);
		smsListView = (ListView) findViewById(R.id.smsList);
		smsListAdapter = new SmsListAdapter(this, R.layout.list_item);
		smsListAdapter.add(new SmsMessage("ToNumber", "Message"));
		smsListView.setAdapter(smsListAdapter);
		setAdapterForNumberEditor();
	}

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

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.send_button :
				String number = numberEditText.getText().toString();
				String message = messageEditText.getText().toString();
				sendMessage(System.currentTimeMillis(), number, message);
				break;

			default :
				break;
		}

	}

	int i = 0;

	private void sendMessage(long currentTimeMillis, String number,
			String message) {
		Intent intent = new Intent(this,
				net.retsat1.starlab.smssender.receiver.SheduleSmsReceiver.class);
		intent.putExtra(SmsMessage.SENDER, "ja");
		intent.putExtra(SmsMessage.DATA, currentTimeMillis + "");
		intent.putExtra(SmsMessage.RECEIVER, number);
		intent.putExtra(SmsMessage.MESSAGE, message);
		i++;// TODO generate Id for sms - if smsId is this same we overwrite
			// sms.
		PendingIntent sender = PendingIntent.getBroadcast(this, 192837 + i,
				intent, PendingIntent.FLAG_ONE_SHOT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (5 * 1000), sender);

	}
}