package net.retsat1.starlab.smssender.dao;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class SmsMessageDaoImpl implements SmsMessageDao {

    private Context mContext;

    public SmsMessageDaoImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean delete(SmsMessage smsMessage) {

        return false;
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
        String[] selectionArgs = new String[] { "" + smsMessage.id };
        mContext.getContentResolver().update(SmsMessage.CONTENT_URI, values, where, selectionArgs);
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
        return true;
    }

    @Override
    public SmsMessage searchByID(int smsId) {
        String where = SmsMessage.SMS_ID + " =?";
        String[] selectionArgs = new String[] { "" + smsId };
        Cursor c = mContext.getContentResolver().query(SmsMessage.CONTENT_URI, null, where, selectionArgs, null);
        SmsMessage smsMessage = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            Log.d("TAG", "count =" + c.getCount());
            smsMessage = new SmsMessage();
            smsMessage.id = smsId;
            smsMessage.message = c.getString(c.getColumnIndex(SmsMessage.MESSAGE));
            smsMessage.number = c.getString(c.getColumnIndex(SmsMessage.RECEIVER));
            c.close();
        }
        return smsMessage;
    }

}
