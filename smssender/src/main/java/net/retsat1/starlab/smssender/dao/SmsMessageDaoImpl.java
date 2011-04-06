package net.retsat1.starlab.smssender.dao;

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
<<<<<<< HEAD
        return false;
=======
        String[] args = new String[] { "" + smsMessage.id };
        String where = SmsMessage.SMS_ID + " =?";
        mContext.getContentResolver().delete(SmsMessage.CONTENT_URI, where, args);
        return true;
>>>>>>> 6899f0ed88b8d37d18c5ba21dd5786da3d0834e3
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
<<<<<<< HEAD
        String[] selectionArgs = new String[] { String.valueOf(smsMessage.id) };
        mContext.getContentResolver().update(SmsMessage.CONTENT_URI, values, where, selectionArgs);
=======
        String[] selectionArgs = new String[] { "" + smsMessage.id };
        contentResolver.update(SmsMessage.CONTENT_URI, values, where, selectionArgs);
        contentResolver.notifyChange(SmsMessage.CONTENT_URI, null);
>>>>>>> 6899f0ed88b8d37d18c5ba21dd5786da3d0834e3
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
<<<<<<< HEAD
            Log.d(TAG, "count =" + c.getCount());
=======
            MyLog.d("TAG", "count =" + c.getCount());
>>>>>>> d07e992fdd6824cf846199e0817c7cf82ab0c999
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
        String[] args = new String[] { "" + id };
        String where = SmsMessage.SMS_ID + " =?";
        mContext.getContentResolver().delete(SmsMessage.CONTENT_URI, where, args);
    }

}
