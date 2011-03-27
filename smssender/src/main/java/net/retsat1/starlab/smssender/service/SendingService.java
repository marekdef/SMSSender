package net.retsat1.starlab.smssender.service;

import net.retsat1.starlab.smssender.receiver.SmsStatusDeliveredReceiver;
import net.retsat1.starlab.smssender.receiver.SmsStatusSendReceiver;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SmsManager;
import android.util.Log;

public class SendingService extends Service {
	private static final String TAG = SendingService.class.getSimpleName();
	private NotificationManager notificationManager;
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart "  +startId);
	    super.onStart(intent, startId);
	}

	
	public void sendSms(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
        //when the SMS has been sent---
        this.registerReceiver(new SmsStatusSendReceiver(), new IntentFilter(SENT));
        //when the SMS has been delivered---
        this.registerReceiver(new SmsStatusDeliveredReceiver(), new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "SendingService.onBind()");
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "SendingService.onCreate()");
		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		super.onCreate();
	}

	private final IBinder mBinder = new Binder() {
        @Override
                protected boolean onTransact(int code, Parcel data, Parcel reply,
                        int flags) throws RemoteException {
        	Log.d(TAG, "SendingService.onTransact()");
            return super.onTransact(code, data, reply, flags);
        }
    };
    public void onDestroy() {
    	Log.d(TAG, "onDestroy()");
    };
    
}
