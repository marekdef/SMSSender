package net.retsat1.starlab.smssender.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class SendingService extends Service {
	private static final String TAG = SendingService.class.getSimpleName();
	private NotificationManager notificationManager;
	
	@Override
	public void onStart(Intent intent, int startId) {
	    super.onStart(intent, startId);
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
}
