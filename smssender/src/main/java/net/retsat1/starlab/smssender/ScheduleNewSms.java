package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class ScheduleNewSms extends Activity {
	private static final String TAG = ScheduleNewSms.class.getSimpleName();

	private Button sendButton;

	private DatePicker dataPicker;

	private AutoCompleteTextView numberEditText;

	private EditText messageEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.schedule);

		dataPicker = (DatePicker) findViewById(R.id.dataPicker);
		numberEditText = (AutoCompleteTextView) findViewById(R.id.numberEditText);
		messageEditText = (EditText) findViewById(R.id.messageEditText);
		sendButton = (Button) findViewById(R.id.send_button);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String number = numberEditText.getText().toString();
				String message = messageEditText.getText().toString();
				sendMessage(System.currentTimeMillis(), number, message);
			}
		});

		setAdapterForNumberEditor();
	}

	private void setAdapterForNumberEditor() {
		ContentResolver content = getContentResolver();
		String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
		String[] PROJECTION = new String[] {

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
			Log.e(TAG, "No contacts to display");
		} else {
			String[] columns = new String[] {
					ContactsContract.Contacts.DISPLAY_NAME,
					ContactsContract.Contacts.HAS_PHONE_NUMBER };
			int[] names = new int[] { R.id.row_display_name,
					R.id.row_phone_number };

			SimpleCursorAdapter adapterPhone = new SimpleCursorAdapter(this,
					R.layout.phone_row_entry, cursor, columns, names);
			startManagingCursor(cursor);
			numberEditText.setAdapter(adapterPhone);

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
