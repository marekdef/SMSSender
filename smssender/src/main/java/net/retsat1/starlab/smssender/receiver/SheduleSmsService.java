package net.retsat1.starlab.smssender.receiver;

import java.util.concurrent.ConcurrentLinkedQueue;

import net.retsat1.starlab.smssender.dto.SmsMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SheduleSmsService extends BroadcastReceiver {

	private static final String TAG = "SheduleSmsService";
	private ConcurrentLinkedQueue<SmsMessage> smsMessages = new ConcurrentLinkedQueue<SmsMessage>();
	
	@Override
	public void onReceive(Context content, Intent intent) {
		Log.d(TAG, "message " + intent.getExtras().getString(SmsMessage.MESSAGE));
		Log.d(TAG, "is Empty " + intent.getExtras().isEmpty());
		Log.d(TAG, "ee " + intent.getExtras().describeContents());
		Log.d(TAG, "message2 " + intent.getStringExtra(SmsMessage.MESSAGE));
		SmsMessage message = createMessage(intent);
		Log.d(TAG, "onReceive " + message);
		Toast.makeText(content, "onReceive " + message, 1500).show();
		smsMessages.add(message);
		
	}

	private SmsMessage createMessage(Intent intent) {
		SmsMessage message = new SmsMessage();
		message.setSender( intent.getStringExtra(SmsMessage.SENDER));
		message.setReceiver(intent.getStringExtra(SmsMessage.RECEIVER));
		message.setMessage(intent.getStringExtra(SmsMessage.MESSAGE));
		return message;
	}

}
