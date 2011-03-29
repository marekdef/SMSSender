package net.retsat1.starlab.smssender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.retsat1.starlab.android.timepicker.DetailedTimePicker;
import net.retsat1.starlab.smssender.dao.SmsMessageDao;
import net.retsat1.starlab.smssender.dao.SmsMessageDaoImpl;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.service.SendingService;
import net.retsat1.starlab.smssender.validators.NumberHighPaidValidator;
import net.retsat1.starlab.smssender.validators.NumberValidator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.Toast;

public class ScheduleNewSms extends Activity {
    private static final String TAG = ScheduleNewSms.class.getSimpleName();

    private Button sendButton;

    private DatePicker datePicker;

    private DetailedTimePicker timePicker;

    private AutoCompleteTextView numberEditText;

    private EditText messageEditText;
    private NumberValidator numberValidator;
    private SmsMessageDao smsMessageDao; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.schedule);
        numberValidator = new NumberHighPaidValidator();
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

                addMessageToSend(number, message);
            }
        });
        smsMessageDao = new SmsMessageDaoImpl(this);
        setAdapterForNumberEditor();
    }

    private void setAdapterForNumberEditor() {


//		new Thread() {
//			public void run() {
//
//				final ContentResolver content = getContentResolver();
//				String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER
//						+ "='1'";
//				String[] PROJECTION = new String[]{
//						ContactsContract.Contacts._ID,
//						ContactsContract.Contacts.DISPLAY_NAME,
//						ContactsContract.Contacts.HAS_PHONE_NUMBER,};
//				Cursor cursor = content.query(
//						ContactsContract.Contacts.CONTENT_URI, PROJECTION,
//						SELECTION, null, null);
//				if (cursor == null) {
//					Log.w(TAG, "No contacts to display");
//				} else {
//					ArrayList<HashMap<String, String>> phones = new ArrayList<HashMap<String, String>>();
//					final SimpleAdapter notes = new SimpleAdapter(
//							ScheduleNewSms.this, phones,
//							R.layout.phone_row_entry, new String[]{"name",
//									"phone"}, new int[]{R.id.row_display_name,
//									R.id.row_phone_number});
//
//					while (cursor.moveToNext()) {
//						String id = cursor.getString(cursor
//								.getColumnIndex(ContactsContract.Contacts._ID));
//						String displayName = cursor
//								.getString(cursor
//										.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//						String where = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//								+ " = ?";
//						String[] selection = new String[]{id};
//						Cursor pCur = getContentResolver()
//								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//										null, where, selection, null);
//						while (pCur.moveToNext()) {
//							// Do something with phones
//							String phone = pCur
//									.getString(pCur
//											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
//
//							String phoneType = pCur
//									.getString(pCur
//											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//
//							// String name =
//							// pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//							String row = displayName + " <" + phone + ">";
//							HashMap<String, String> phoneRow = new HashMap<String, String>();
//							phoneRow.put("name", displayName);
//							phoneRow.put("phone", phone);
//							phones.add(phoneRow);
//							Log.i("Pratik", "PHONE: " + displayName + " <"
//									+ phone + "> ");
//							Log.i("Pratik", "PHONE TYPE: " + phoneType);
//						}
//						pCur.close();
//					}
//					ScheduleNewSms.this.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							Log.d(TAG, "one");
//							//numberEditText.setAdapter(notes);
//							Log.d(TAG, "two");
//						}
//					});
//				};
//
//			}
//		}.start();

    }
    
    
    private static int idCode =0;
    private long DATE_27_III_2011 = 1301258571993L;
    private void addMessageToSend(String number, String message) {
    	logTime();
        Log.d(TAG, "sendMessage number " + number + " message " + message);
        idCode++;
        if (numberValidator.isValid(number)){
        	Toast.makeText(this, getResources().getString(R.string.this_sms_is_paid), 2000).show();
        	return;
        }
        long timeNow = System.currentTimeMillis();
        long scheduledTimeMillis = getSetupTime();
        int reqCode = (int)((timeNow-DATE_27_III_2011)+idCode);
        SmsMessage smsMessage = createNewMessage(reqCode, number, message, scheduledTimeMillis);
        smsMessageDao.insert(smsMessage);
        alarmSetup(smsMessage);
    }

	private SmsMessage createNewMessage(int reqCode, String number,
			String message, long scheduledTimeMillis) {
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.id = reqCode;
		smsMessage.number = number;
		smsMessage.message = message;
		smsMessage.deliveryDate = scheduledTimeMillis;
		smsMessage.dateOfSetup = System.currentTimeMillis();
		smsMessage.dateOfStatus =  System.currentTimeMillis();
		smsMessage.messageStatus = SmsMessage.STATUS_UNSENT;
		smsMessage.deliveryStatus = SmsMessage.STATUS_UNSENT;
		
		return smsMessage;
	}

	private void alarmSetup(SmsMessage smsMessage) {
		
		Log.d(TAG, "Data when" + smsMessage.deliveryDate );
		Intent intent = new Intent(this, SendingService.class);
		intent.putExtra(SmsMessage.SMS_ID, smsMessage.id);
        
        PendingIntent pendingIntent = PendingIntent.getService(this, smsMessage.id, intent, PendingIntent.FLAG_ONE_SHOT);
        
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, smsMessage.deliveryDate, pendingIntent);
		
	}

	

	private long getSetupTime() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute(), timePicker.getCurrentSecond());
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	private void logTime() {
		Log.d(TAG, "year: " + datePicker.getYear());
        Log.d(TAG, "month: " + datePicker.getMonth());
        Log.d(TAG, "day: " + datePicker.getDayOfMonth());
        Log.d(TAG, "hh: " + timePicker.getCurrentHour());
        Log.d(TAG, "mm: " + timePicker.getCurrentMinute());
        Log.d(TAG, "ss: " + timePicker.getCurrentSecond());
	}

}
