package net.retsat1.starlab.smssender;


import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsListAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class ScheduledSmsList extends Activity implements OnClickListener {
	private static final String TAG = "ScheduledSmsList";

	private static final int REQUEST_CODE = 0;

	Button sendButton;

	private DatePicker dataPicker;

	private EditText numberEditText;

	private EditText messageEditText;

	private ListView smsListView;

	private SmsListAdapter smsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		dataPicker = (DatePicker) findViewById(R.id.dataPicker);
		numberEditText = (EditText) findViewById(R.id.numberEditText);
		messageEditText = (EditText) findViewById(R.id.messageEditText);
		sendButton = (Button) findViewById(R.id.send_button);
		sendButton.setOnClickListener(this);
		smsListView =  (ListView)findViewById(R.id.smsList);
		smsListAdapter = new SmsListAdapter(this, R.layout.list_item);
		smsListAdapter.add(new SmsMessage("ToNumber", "Message"));
		smsListView.setAdapter(smsListAdapter);
		
	}
	
	
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.send_button:
			String number = numberEditText.getText().toString();
			String message = messageEditText.getText().toString();
			sendMessage(System.currentTimeMillis(), number, message);
			break;

		default:
			break;
		}
		
	}

	int i = 0;
	private void sendMessage(long currentTimeMillis, String number,
			String message) {
		Intent intent = new Intent(this, net.retsat1.starlab.smssender.receiver.SheduleSmsReceiver.class);
		intent.putExtra(SmsMessage.SENDER, "ja");
		intent.putExtra(SmsMessage.DATA, currentTimeMillis+"");
		intent.putExtra(SmsMessage.RECEIVER, number);
		intent.putExtra(SmsMessage.MESSAGE, message);
		i++;
		PendingIntent sender = PendingIntent.getBroadcast(this, 192837+i, intent, PendingIntent.FLAG_ONE_SHOT);


		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), sender);
		
	}
}