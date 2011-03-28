package net.retsat1.starlab.smssender.receiver;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsStatusSendReceiver extends BroadcastReceiver{

	private static final String TAG = "SmsStatusSendReceiver";

	public SmsStatusSendReceiver() {
	
	}
	
	@Override
	public void onReceive(Context context, Intent i) {
		int resultCode = getResultCode();
		Log.d(TAG, "getResultCode() " + resultCode);
		Log.d(TAG, "intent  " + i + " d=" + i.describeContents());
		int smsId =  i.getExtras().getInt(SmsMessage.SMS_ID);
		Log.d(TAG, "smsId() " + smsId);
		updateSmsStatus(smsId, resultCode);
		switch (resultCode)
        {
		
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS sent", 
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(context, "Generic failure", 
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "No service", 
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(context, "Null PDU", 
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(context, "Radio off", 
                        Toast.LENGTH_SHORT).show();
                break;
        }

		
	}

	private void updateSmsStatus(int smsId, int resultCode) {
		
		
	}

}
