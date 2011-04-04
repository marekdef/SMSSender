package net.retsat1.starlab.smssender.receiver;

import net.retsat1.starlab.smssender.dao.SmsMessageDaoImpl;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsStatusSendReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsStatusSendReceiver";
    private SmsMessageDaoImpl smsMessageDao;
    private NotificationManager notificationManager;

    public SmsStatusSendReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent i) {
        int resultCode = getResultCode();
        Log.d(TAG, "getResultCode() " + resultCode);
        int smsId = i.getExtras().getInt(SmsMessage.SMS_ID);
        Log.d(TAG, "smsId() " + smsId);
        updateSmsStatus(context, smsId, resultCode);
        CharSequence contentText = null;
        switch (resultCode) {

        case Activity.RESULT_OK:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_send_status);
            sendNotification(context, contentText, smsId);
            break;
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_error_failure_status);
            sendNotification(context, contentText, smsId);
            break;
        case SmsManager.RESULT_ERROR_NO_SERVICE:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_no_service_status);
            sendNotification(context, contentText, smsId);
            break;
        case SmsManager.RESULT_ERROR_NULL_PDU:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_null_pdu_status);
            sendNotification(context, contentText, smsId);
            break;
        case SmsManager.RESULT_ERROR_RADIO_OFF:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_radio_off_status);
            sendNotification(context, contentText, smsId);
            break;
        default:
            contentText = context.getResources().getString(net.retsat1.starlab.smssender.R.string.sms_send_unknown_status);
            sendNotification(context, contentText, smsId);
            break;
        }

    }

    private void sendNotification(Context context, CharSequence contentText, int smsId) {
        Toast.makeText(context, contentText, Toast.LENGTH_SHORT).show();
    }

    private void updateSmsStatus(Context context, int smsId, int resultCode) {
        smsMessageDao = new SmsMessageDaoImpl(context);
        SmsMessage smsMessage = smsMessageDao.searchByID(smsId);
        smsMessage.messageStatus = resultCode;
        smsMessageDao.update(smsMessage);
    }

}
