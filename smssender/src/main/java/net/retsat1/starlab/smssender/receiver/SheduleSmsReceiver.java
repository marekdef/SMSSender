package net.retsat1.starlab.smssender.receiver;

import java.util.concurrent.ConcurrentLinkedQueue;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SheduleSmsReceiver extends BroadcastReceiver {

	private static final String TAG = "SheduleSmsService";
	private ConcurrentLinkedQueue<SmsMessage> smsMessages = new ConcurrentLinkedQueue<SmsMessage>();
	private Context context;

	@Override
	public void onReceive(Context content, Intent intent) {
		this.context = content;
		SmsMessage message = createMessage(intent);
		Log.d(TAG, "onReceive " + message);
		Toast.makeText(content, "onReceive " + message, 1500).show();
		smsMessages.add(message);

	}

	public void sendSms(String phoneNumber, String message) {

		
		String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
            new Intent(DELIVERED), 0);
        //when the SMS has been sent---
        context.registerReceiver(new SmsStatusSendReceiver(), new IntentFilter(SENT));
        //when the SMS has been delivered---
        context.registerReceiver(new SmsStatusDeliveredReceiver(), new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

	private SmsMessage createMessage(Intent intent) {
		SmsMessage message = new SmsMessage();
		message.setSender(intent.getStringExtra(SmsMessage.SENDER));
		message.setReceiver(intent.getStringExtra(SmsMessage.RECEIVER));
		message.setMessage(intent.getStringExtra(SmsMessage.MESSAGE));
		return message;
	}

}
