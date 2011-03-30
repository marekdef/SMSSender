package net.retsat1.starlab.smssender.service;

import net.retsat1.starlab.smssender.dao.SmsMessageDao;
import net.retsat1.starlab.smssender.dao.SmsMessageDaoImpl;
import net.retsat1.starlab.smssender.dto.SmsMessage;
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
    private SmsMessageDao smsMessageDao;

    @Override
    public void onStart(Intent intent, int startId) {
        int smsId = intent.getExtras().getInt(SmsMessage.SMS_ID);
        Log.d(TAG, "onStart " + startId + " smsId " + smsId + intent.describeContents());
        super.onStart(intent, startId);
            sendSms(smsId);        
    }

    private void sendSms(int smsId) {
        Log.d(TAG, "smsId " + smsId);
        SmsMessage smsMessage = smsMessageDao.searchByID(smsId);
        try {
            sendSms(smsMessage);
        } catch (Exception e) {
            e.printStackTrace();
            smsMessage.deliveryStatus = -110; //error
            smsMessageDao.update(smsMessage);
        }
    }

    public void sendSms(SmsMessage smsMessage) throws Exception{
        try {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";
            Intent sendIntent = new Intent(SENT);
            Intent deliveredIntent = new Intent(DELIVERED);
            sendIntent.putExtra(SmsMessage.SMS_ID, smsMessage.id);
            deliveredIntent.putExtra(SmsMessage.SMS_ID, smsMessage.id);
            PendingIntent sentPI = PendingIntent.getBroadcast(this, smsMessage.id, sendIntent, 0);
    
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, smsMessage.id, deliveredIntent, 0);
            // when the SMS has been sent---
            this.registerReceiver(new SmsStatusSendReceiver(), new IntentFilter(SENT));
            // when the SMS has been delivered---
            this.registerReceiver(new SmsStatusDeliveredReceiver(), new IntentFilter(DELIVERED));
            SmsManager sms = SmsManager.getDefault();
            updateSmsStatusToSending(smsMessage);
            sms.sendTextMessage(smsMessage.number, null, smsMessage.message, sentPI, deliveredPI);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new Exception("Sms not send empty body  -111");

        }catch (Throwable e) {
            e.printStackTrace();
            throw new Exception("Sms not send -110");
        }
    }

    private void updateSmsStatusToSending(SmsMessage smsMessage) {
        smsMessage.messageStatus = SmsMessage.STATUS_SENDING;
        smsMessage.dateOfStatus = System.currentTimeMillis();
        smsMessageDao.update(smsMessage);
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "SendingService.onBind()");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "SendingService.onCreate()");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        smsMessageDao = new SmsMessageDaoImpl(this);
        super.onCreate();
    }

    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.d(TAG, "SendingService.onTransact()");
            return super.onTransact(code, data, reply, flags);
        }
    };

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
    };

}
