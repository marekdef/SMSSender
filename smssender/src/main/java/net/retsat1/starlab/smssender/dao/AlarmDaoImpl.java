package net.retsat1.starlab.smssender.dao;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

public class AlarmDaoImpl implements AlarmDao {

    private static final String TAG = "AlarmDaoImpl";
    private Context context;
    private Class<? extends Service> service;

    public AlarmDaoImpl(Context context, Class<? extends Service> service) {
        this.context = context;
        this.service = service;
    }

    @Override
    public void setAlarm(SmsMessage smsMessage) {
        MyLog.d(TAG, "Data when" + smsMessage.deliveryDate);
        Intent intent = new Intent(context, service);
        intent.putExtra(SmsMessage.SMS_ID, smsMessage.id);
        PendingIntent pendingIntent = PendingIntent.getService(context, smsMessage.id, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, smsMessage.deliveryDate, pendingIntent);
    }

    @Override
    public void deleteAlarm(SmsMessage smsMessage) {
        MyLog.d(TAG, "Data when" + smsMessage.deliveryDate);
        Intent intent = new Intent(context, service);
        intent.putExtra(SmsMessage.SMS_ID, smsMessage.id);
        PendingIntent pendingIntent = PendingIntent.getService(context, smsMessage.id, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void deleteAlarm(Integer id) {
        MyLog.d(TAG, "deleteAlarm " + id);
        Intent intent = new Intent(context, service);
        intent.putExtra(SmsMessage.SMS_ID, id);
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
