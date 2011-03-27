package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.service.SendingService;
import net.retsat1.starlab.android.timepicker.DetailedTimePicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.NetworkInfo.DetailedState;
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

	private DatePicker datePicker;
	
	private DetailedTimePicker detailedTimePicker;

	private AutoCompleteTextView numberEditText;

	private EditText messageEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.schedule);

		datePicker = (DatePicker) findViewById(R.id.dataPicker);
		detailedTimePicker = (DetailedTimePicker) (R.id.detailedTimePicker);
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

		ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER, };

		Cursor cursor = content.query(ContactsContract.Contacts.CONTENT_URI,
				PROJECTION, SELECTION, null, null);

		if (cursor == null) {
			Log.w(TAG, "No contacts to display");
		} else {
			while (cursor.moveToNext()) {
				String data = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				int hasNumber = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

				Log.d(TAG, "data " + data + " hasNumber=" + hasNumber);
			}

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

	private void sendMessage(long currentTimeMillis, String number,
			String message) {
		Intent intent = new Intent(this, SendingService.class);
		intent.putExtra(SmsMessage.MESSAGE,message);
		intent.putExtra(SmsMessage.RECEIVER, (number));
		
		datePicker.getDa
	
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
				PendingIntent.FLAG_ONE_SHOT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeMillis + 10000,
				pendingIntent);
	}

}
