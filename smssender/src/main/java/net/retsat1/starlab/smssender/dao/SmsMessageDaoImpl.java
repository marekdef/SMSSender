package net.retsat1.starlab.smssender.dao;

import java.util.ArrayList;
import java.util.List;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SmsMessageDaoImpl implements SmsMessageDao {
    private static final String TAG = SmsMessageDaoImpl.class.getSimpleName();

    private Context mContext;
    private ContentResolver contentResolver;

    public SmsMessageDaoImpl(Context context) {
        this.mContext = context;
        this.contentResolver = mContext.getContentResolver();
    }

    @Override
    public boolean delete(SmsMessage smsMessage) {
        String[] args = new String[] { "" + smsMessage.id };
        String where = SmsMessage.SMS_ID + " =?";
        mContext.getContentResolver().delete(SmsMessage.CONTENT_URI, where, args);
        return true;
    }

    @Override
    public boolean update(SmsMessage smsMessage) {
        ContentValues values = new ContentValues();

        values.put(SmsMessage.MESSAGE, smsMessage.message);
        values.put(SmsMessage.RECEIVER, smsMessage.number);

        values.put(SmsMessage.MESSAGE_STATUS, smsMessage.messageStatus);
        values.put(SmsMessage.SETUP_DATE, smsMessage.dateOfSetup);
        values.put(SmsMessage.DELIVERY_STATUS, smsMessage.deliveryStatus);
        values.put(SmsMessage.STATUS_DATE, smsMessage.dateOfStatus);
        values.put(SmsMessage.DELIVERY_DATE, smsMessage.deliveryDate);
        String where = SmsMessage.SMS_ID + " =?";
        String[] selectionArgs = new String[] { String.valueOf(smsMessage.id) };
        contentResolver.update(SmsMessage.CONTENT_URI, values, where, selectionArgs);
        contentResolver.notifyChange(SmsMessage.CONTENT_URI, null);
        return true;
    }

    @Override
    public boolean insert(SmsMessage smsMessage) {
        ContentValues values = new ContentValues();
        values.put(SmsMessage.SMS_ID, smsMessage.id);
        values.put(SmsMessage.MESSAGE, smsMessage.message);
        values.put(SmsMessage.RECEIVER, smsMessage.number);

        values.put(SmsMessage.MESSAGE_STATUS, smsMessage.messageStatus);
        values.put(SmsMessage.SETUP_DATE, smsMessage.dateOfSetup);
        values.put(SmsMessage.DELIVERY_STATUS, smsMessage.deliveryStatus);
        values.put(SmsMessage.STATUS_DATE, smsMessage.dateOfStatus);
        values.put(SmsMessage.DELIVERY_DATE, smsMessage.deliveryDate);
        mContext.getContentResolver().insert(SmsMessage.CONTENT_URI, values);
        contentResolver.notifyChange(SmsMessage.CONTENT_URI, null);
        return true;
    }

    @Override
    public SmsMessage searchByID(int smsId) {
        String where = SmsMessage.SMS_ID + " =?";
        String[] selectionArgs = new String[] { String.valueOf(smsId) };
        Cursor c = mContext.getContentResolver().query(SmsMessage.CONTENT_URI, null, where, selectionArgs, null);
        SmsMessage smsMessage = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            MyLog.d(TAG, "count =" + c.getCount());
            smsMessage = new SmsMessage();
            smsMessage.id = smsId;
            smsMessage.message = c.getString(c.getColumnIndex(SmsMessage.MESSAGE));
            smsMessage.number = c.getString(c.getColumnIndex(SmsMessage.RECEIVER));
            smsMessage.deliveryDate = c.getLong(c.getColumnIndex(SmsMessage.DELIVERY_DATE));
            smsMessage.deliveryStatus = c.getInt(c.getColumnIndex(SmsMessage.DELIVERY_STATUS));
            smsMessage.messageStatus = c.getInt(c.getColumnIndex(SmsMessage.MESSAGE_STATUS));
            smsMessage.dateOfSetup = c.getLong(c.getColumnIndex(SmsMessage.SETUP_DATE));
            smsMessage.dateOfStatus = c.getLong(c.getColumnIndex(SmsMessage.STATUS_DATE));
            c.close();
        }
        return smsMessage;
    }

    @Override
    public void delete(int id) {
        String[] args = new String[] { String.valueOf(id) };
        String where = SmsMessage.SMS_ID + " =?";
        mContext.getContentResolver().delete(SmsMessage.CONTENT_URI, where, args);
    }

    @Override
    public List<SmsMessage> getAllMessages() {
        ArrayList<SmsMessage> list = new ArrayList<SmsMessage>();
        Cursor c = mContext.getContentResolver().query(SmsMessage.CONTENT_URI, null, null, null, null);
        MyLog.d("TAG", "count =" + c.getCount());
        while (c.moveToNext()) {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.id = c.getInt(c.getColumnIndex(SmsMessage.SMS_ID));
            smsMessage.message = c.getString(c.getColumnIndex(SmsMessage.MESSAGE));
            smsMessage.number = c.getString(c.getColumnIndex(SmsMessage.RECEIVER));
            smsMessage.deliveryDate = c.getLong(c.getColumnIndex(SmsMessage.DELIVERY_DATE));
            smsMessage.deliveryStatus = c.getInt(c.getColumnIndex(SmsMessage.DELIVERY_STATUS));
            smsMessage.messageStatus = c.getInt(c.getColumnIndex(SmsMessage.MESSAGE_STATUS));
            smsMessage.dateOfSetup = c.getLong(c.getColumnIndex(SmsMessage.SETUP_DATE));
            smsMessage.dateOfStatus = c.getLong(c.getColumnIndex(SmsMessage.STATUS_DATE));
            list.add(smsMessage);
        }
        c.close();
        return list;

    }

}
