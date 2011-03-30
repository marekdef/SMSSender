package net.retsat1.starlab.smssender.ui.adapter;

import java.util.concurrent.ConcurrentLinkedQueue;

import net.retsat1.starlab.smssender.R;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter;

public class SmsCursorAdapter extends SimpleCursorAdapter implements OnCheckedChangeListener {

    private static final String TAG = "SmsCursorAdapter";
    private Cursor c;
    private Context context;
    private ConcurrentLinkedQueue<Integer> checkList = new ConcurrentLinkedQueue<Integer>();

    public SmsCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
        c.setNotificationUri(context.getContentResolver(), SmsMessage.CONTENT_URI);
        
    }

    @Override
    public synchronized View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.list_item, parent, false);
        
        CheckBox cb = (CheckBox) v.findViewById(R.id.checked);
        Integer id = cursor.getInt(cursor.getColumnIndex(SmsMessage.SMS_ID));
        Log.d(TAG, "newView ID=" + id + " checked= " + cb.isChecked());
        cb.setOnCheckedChangeListener(this);
        cb.setTag(id);
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
              v = super.getView(position, convertView, parent);
        }
        this.c.moveToPosition(position);
        Integer i = this.c.getInt(this.c.getColumnIndex(SmsMessage.SMS_ID));
        Log.d(TAG, "IDID = " + i);
        CheckBox cBox = (CheckBox) v.findViewById(R.id.checked);
        cBox.setTag(i);
        cBox.setOnCheckedChangeListener(this);
        boolean b = checkList.contains(i);
        cBox.setChecked(b);
        Log.d(TAG, "position " + position);
        return v;
    }

    public void deleteAllCheckedItems() {

        String where = SmsMessage.SMS_ID + " =?";
        if (!checkList.isEmpty()) {
            for (Integer id : checkList) {
                Log.d(TAG, "Delete " + id);
                String[] args = new String[] { "" + id };
                context.getContentResolver().delete(SmsMessage.CONTENT_URI, where, args);
                checkList.remove(id);
            }
        }
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "TAG " + buttonView.getTag());
        if (isChecked) {
            checkList.add((Integer) buttonView.getTag());
        } else {
            checkList.remove((Integer) buttonView.getTag());
        }
        Log.d(TAG, "checkList " + checkList.size());
    }

    public void selectAllItems() {
        if (getCount() > 0) {
            c.moveToFirst();
            do {
                Integer id = c.getInt(c.getColumnIndex(SmsMessage.SMS_ID)); // crashes
                                                                            // here
                checkList.add(id);
            } while (c.moveToNext());
            notifyDataSetInvalidated();
            notifyDataSetChanged();
        }
    }
}