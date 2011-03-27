package net.retsat1.starlab.smssender.ui.adapter;

import java.util.ArrayList;

import net.retsat1.starlab.smssender.R;
import net.retsat1.starlab.smssender.dto.SmsMessage;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;

public class SmsCursorAdapter extends SimpleCursorAdapter{

	private static final String TAG = "SmsCursorAdapter";
	private Cursor c;
	private Context context;
	private ArrayList<Integer> checkList = new ArrayList<Integer>();
	public SmsCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, null);
		}
        this.c.moveToPosition(position);		
		CheckBox cBox = (CheckBox) v.findViewById(R.id.checked);
		Integer i = Integer.parseInt(this.c.getString(this.c.getColumnIndex(SmsMessage.SMS_ID)));
		Log.d(TAG, "checkList + " + i + " " + checkList.size());
		checkList.add(i);
		cBox.setTag(Integer.parseInt(this.c.getString(this.c.getColumnIndex(SmsMessage.SMS_ID))));
		return(v);
	}

}
