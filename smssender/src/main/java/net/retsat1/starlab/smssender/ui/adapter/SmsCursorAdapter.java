package net.retsat1.starlab.smssender.ui.adapter;

import java.util.List;

import net.retsat1.starlab.smssender.R;
import net.retsat1.starlab.smssender.dao.AlarmDao;
import net.retsat1.starlab.smssender.dao.AlarmDaoImpl;
import net.retsat1.starlab.smssender.dao.SmsMessageDao;
import net.retsat1.starlab.smssender.dao.SmsMessageDaoImpl;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.service.SendingService;
import net.retsat1.starlab.smssender.utils.DateUtils;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Cursor to manage sms data.
 * 
 * @author mario
 * 
 */
public class SmsCursorAdapter extends SimpleCursorAdapter {

    private static final String TAG = "SmsCursorAdapter";
    private Cursor c;
    private Context context;
    static String[] valuePosition = { SmsMessage.RECEIVER, SmsMessage.MESSAGE, SmsMessage.MESSAGE_STATUS };
    static int[] uiPosition = { R.id.numberText, R.id.messageText, R.id.stat };
    private int[] mFrom;
    private LayoutInflater li;
    private SmsMessageDao smsMessageDao;
    private AlarmDao alarmDao;

    public SmsCursorAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, valuePosition, uiPosition);
        this.c = c;
        this.context = context;
        findColumns(valuePosition);
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        c.setNotificationUri(context.getContentResolver(), SmsMessage.CONTENT_URI);
        smsMessageDao = new SmsMessageDaoImpl(context);
        alarmDao = new AlarmDaoImpl(context, SendingService.class);

    }

    /**
     * Create a map from an array of strings to an array of column-id integers
     * in mCursor. If mCursor is null, the array will be discarded.
     * 
     * @param from
     *            the Strings naming the columns of interest
     */
    private void findColumns(String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyLog.d(TAG, "bindView view " + view + " context  " + context + " cursor " + cursor);
        final ViewBinder binder = getViewBinder();
        final int count = uiPosition.length;
        final int[] from = mFrom;
        final int[] to = uiPosition;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                boolean bound = false;
                if (binder != null) {
                    MyLog.d(TAG, "from[i]  " + from[i] + " " + valuePosition[i]);
                    bound = binder.setViewValue(v, cursor, from[i]);
                }

                if (!bound) {
                    String text = cursor.getString(from[i]);
                    if (text == null) {
                        text = "";
                    }

                    if (v instanceof TextView) {
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        setViewImage((ImageView) v, text);
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " + " view that can be bounds by this SimpleCursorAdapter");
                    }
                }
            }
        }
    }

    /**
     * Tested on android 2.1
     * 
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyLog.d(TAG, "convertView " + convertView);
        View v = null;
        if (convertView == null) {
            convertView = li.inflate(R.layout.list_item, parent, false);
        }
        MyLog.d(TAG, "v = " + " convertView = " + convertView);
        v = super.getView(position, convertView, parent);
        this.c.moveToPosition(position);

        int id = this.c.getInt(this.c.getColumnIndex(SmsMessage.SMS_ID));
        SmsMessage smsMessage = smsMessageDao.searchByID(id);
        TextView statusTextView = (TextView) v.findViewById(R.id.stat);
        TextView countDownTextView = (TextView) v.findViewById(R.id.timeText);
        String status = getStatusText(smsMessage.messageStatus, smsMessage.deliveryStatus);
        MyLog.d(TAG, "smsMessage.deliveryDate  " + smsMessage.deliveryDate + " SS= " + System.currentTimeMillis());
        String counting = getCountDownTime(smsMessage);
        countDownTextView.setText(counting);
        statusTextView.setText(status);
        MyLog.d(TAG, "position " + position);
        return v;
    }

    private String getCountDownTime(SmsMessage smsMessage) {
        String countDown = " ";
        if (smsMessage.messageStatus == SmsMessage.STATUS_UNSENT) {
            long time = (smsMessage.deliveryDate - System.currentTimeMillis()) / 1000;
            countDown = DateUtils.changeSecToNiceDate(time);
        }
        return countDown;
    }

    private String getStatusText(Integer messageStatus, Integer deliveryStatus) {
        String status = "";
        switch (messageStatus) {
        case SmsMessage.STATUS_UNSENT:
            status += "Message not send";
            break;
        case SmsMessage.STATUS_SENDING:
            status += "Message sending";
            break;
        case Activity.RESULT_OK:
            status += "Message send OK";
            break;
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            status += "Message generic error";
            break;
        case SmsManager.RESULT_ERROR_NO_SERVICE:
            status += "No service error";
            break;
        case SmsManager.RESULT_ERROR_NULL_PDU:
            status += "Error NULL PDU";
            break;
        case SmsManager.RESULT_ERROR_RADIO_OFF:
            status += "Radio off";
            break;
        default:
            status += "Message unknown status";
        }
        status += "|";
        switch (deliveryStatus) {
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            status += "General raport failure";
            break;
        case Activity.RESULT_OK:
            status += "Delivered";
            break;
        default:
            status += "delivery status unknown";
            break;
        }
        return status;

    }

    public void delete(int position) {
        c.moveToPosition(position);
        Integer id = c.getInt(c.getColumnIndex(SmsMessage.SMS_ID));
        alarmDao.deleteAlarm(id);
        smsMessageDao.delete(id);
    }

    public int getSmsIDByPosition(int position) {
        c.moveToPosition(position);
        Integer id = c.getInt(c.getColumnIndex(SmsMessage.SMS_ID));
        return id;
    }

    public void deleteOldTasks() {
        List<SmsMessage> messages = smsMessageDao.getAllMessages();
        for (SmsMessage smsMessage : messages) {
            if (smsMessage.messageStatus == Activity.RESULT_OK) {
                smsMessageDao.delete(smsMessage.id);
            }
        }
        notifyDataSetInvalidated();
        notifyDataSetChanged();

    }
}
