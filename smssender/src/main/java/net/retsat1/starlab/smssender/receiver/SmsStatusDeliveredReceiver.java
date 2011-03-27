package net.retsat1.starlab.smssender.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsStatusDeliveredReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
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

}
