package net.retsat1.starlab.smssender.receiver;

import net.retsat1.starlab.smssender.dao.SmsMessageDao;
import net.retsat1.starlab.smssender.dao.SmsMessageDaoImpl;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsStatusDeliveredReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsStatusDeliveredReceiver";
	private SmsMessageDao smsMessageDao;
	
	public SmsStatusDeliveredReceiver() {
		
	}
	
	@Override
	public void onReceive(Context context, Intent i) {
		
		int resultCode = getResultCode();
		Log.d(TAG, "getResultCode() " + resultCode);
		Log.d(TAG, "intent  " + i + " d=" + i.describeContents());
		int smsId =  i.getExtras().getInt(SmsMessage.SMS_ID);
		Log.d(TAG, "smsId() " + smsId);
		updateSmsStatus(context, smsId, resultCode);
		switch (getResultCode()) {
		case Activity.RESULT_OK:
			Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
			break;
		case Activity.RESULT_CANCELED:
			Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT)
					.show();
			break;
		}

	}

	private void updateSmsStatus(Context context, int smsId, int resultCode) {
		smsMessageDao = new SmsMessageDaoImpl(context);
		SmsMessage smsMessage =  smsMessageDao.searchByID(smsId);
		smsMessage.deliveryStatus = resultCode;
		smsMessageDao.update(smsMessage);
	}

}
