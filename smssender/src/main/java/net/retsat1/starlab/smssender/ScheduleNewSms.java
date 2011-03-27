package net.retsat1.starlab.smssender;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.retsat1.starlab.android.timepicker.DetailedTimePicker;
import net.retsat1.starlab.smssender.service.SendingService;
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

	private DatePicker datePicker;

	private AutoCompleteTextView numberEditText;

	private EditText messageEditText;

	private PendingIntent pendingIntent;

	private DetailedTimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.schedule);

		datePicker = (DatePicker) findViewById(R.id.dataPicker);
		timePicker = (DetailedTimePicker) findViewById(R.id.detailedTimePicker);
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

		pendingIntent = PendingIntent.getService(this, 0, new Intent(this,
				SendingService.class), 0);
	}

	private void setAdapterForNumberEditor() {
		ContentResolver content = getContentResolver();
		String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
		String[] PROJECTION = new String[] {

		ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER, };

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
			Log.w(TAG, "No contacts to display");
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

	private void sendMessage(long currentTimeMillis, String number,
			String message) {
		
		Log.d(TAG, "sendMessage number " + number  + " message " + message);
		Log.d(TAG, "year: " + datePicker.getYear());
		Log.d(TAG, "month: " + datePicker.getMonth());
		Log.d(TAG, "day: " + datePicker.getDayOfMonth());
		Log.d(TAG, "hh: " + timePicker.getCurrentHour());
		Log.d(TAG, "mm: " + timePicker.getCurrentMinute());
		Log.d(TAG, "ss: " + timePicker.getCurrentSecond());
		Date d =new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), timePicker.getCurrentSecond());
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.YEAR, datePicker.getYear());
		c.set(Calendar.MONTH, datePicker.getMonth());
		c.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
		
		c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		c.set(Calendar.SECOND, timePicker.getCurrentSecond());
		c.set(Calendar.MILLISECOND, 0);
		Log.d(TAG, " data  " + d.getTime() + " curren " + currentTimeMillis + " c="+c.getTimeInMillis());
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP,
				c.getTimeInMillis(), pendingIntent);
	}

}




