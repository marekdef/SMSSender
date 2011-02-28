package com.android.widget;

import net.retsat1.starlab.smssender.R;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;


public class DetailedTimePicker extends LinearLayout {
	private static final String TAG = DetailedTimePicker.class.getSimpleName();

	private NumberPicker mHourPicker;
	private NumberPicker mMinutePicker;
	private NumberPicker mSecondPicker;

	public DetailedTimePicker(Context context) {
		this(context, null);
	}

	public DetailedTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.detailed_time_picker, this, true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "onMeasure");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
