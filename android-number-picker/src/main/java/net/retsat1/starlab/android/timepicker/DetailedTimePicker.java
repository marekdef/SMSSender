package net.retsat1.starlab.android.timepicker;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.TimeZone;

import net.retsat1.starlab.android.timepicker.NumberPicker.OnChangedListener;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

/**
 * A view for selecting a time with seconds
 * 
 */
public class DetailedTimePicker extends LinearLayout implements OnChangedListener {
    private static final String TAG = DetailedTimePicker.class.getSimpleName();

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view
         *            The view associated with this listener.
         * @param hourOfDay
         *            The current hour.
         * @param minute
         *            The current minute.
         */
        void onTimeChanged(DetailedTimePicker view, int hourOfDay, int minute, int second);
    }

    /**
     * A no-op callback used in the constructor to avoid null checks later in
     * the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(DetailedTimePicker view, int hourOfDay, int minute, int second) {
        }
    };

    // state
    private int mCurrentHour = 0; // 0-23
    private int mCurrentMinute = 0; // 0-59
    private int mCurrentSecond = 0; // 0-59

    private boolean mIs24HourView = true;

    private boolean mIsAm;
    // ui components
    private final NumberPicker mHourPicker;
    private final NumberPicker mMinutePicker;
    private final NumberPicker mSecondPicker;
    private final Button mAmPmButton;
    private final String mAmText;

    private final String mPmText;

    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    public DetailedTimePicker(Context context) {
        this(context, null);
    }

    public DetailedTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.detailed_time_picker, this, true);

        mHourPicker = (NumberPicker) findViewById(R.id.hour);
        mHourPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mMinutePicker = (NumberPicker) findViewById(R.id.minute);
        mMinutePicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mSecondPicker = (NumberPicker) findViewById(R.id.second);
        mSecondPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mHourPicker.setOnChangeListener(this);
        mMinutePicker.setOnChangeListener(this);
        mSecondPicker.setOnChangeListener(this);

        mAmPmButton = (Button) findViewById(R.id.amPm);

        configurePickerRanges();

        // initialize to current time
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        // by default we're not in 24 hour mode
        setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        setCurrentMinute(cal.get(Calendar.MINUTE));
        setCurrentSecond(cal.get(Calendar.SECOND));

        mIsAm = (mCurrentHour < 12);

        /* Get the localized am/pm strings and use them in the spinner */
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] dfsAmPm = dfs.getAmPmStrings();
        mAmText = dfsAmPm[Calendar.AM];
        mPmText = dfsAmPm[Calendar.PM];
        mAmPmButton.setText(mIsAm ? mAmText : mPmText);
        mAmPmButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                requestFocus();
                if (mIsAm) {

                    // Currently AM switching to PM
                    if (mCurrentHour < 12) {
                        mCurrentHour += 12;
                    }
                } else {

                    // Currently PM switching to AM
                    if (mCurrentHour >= 12) {
                        mCurrentHour -= 12;
                    }
                }
                mIsAm = !mIsAm;
                mAmPmButton.setText(mIsAm ? mAmText : mPmText);
                onTimeChanged();
            }
        });

        if (!isEnabled()) {
            setEnabled(false);
        }
        mMinutePicker.changeCurrent(mMinutePicker.getCurrent() + 1);// add one
                                                                    // minute
        mSecondPicker.changeCurrent(0); // set 0 second by default
    }

    private void configurePickerRanges() {
        if (mIs24HourView) {
            mHourPicker.setRange(0, 23);
            mHourPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
            mAmPmButton.setVisibility(View.GONE);
        } else {
            mHourPicker.setRange(1, 12);
            mHourPicker.setFormatter(null);
            mAmPmButton.setVisibility(View.VISIBLE);
        }
        mMinutePicker.setRange(0, 59);
        mSecondPicker.setRange(0, 59);
    }

    /**
     * @return The current hour (0-23).
     */
    public Integer getCurrentHour() {
        return mCurrentHour;
    }

    /**
     * Override so we are in complete control of save / restore for this widget.
     */
    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    public int getCurrentSecond() {
        return mCurrentSecond;
    }

    private void onTimeChanged() {
        if (mOnTimeChangedListener != null) {
            mOnTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute(), getCurrentSecond());
        }
    }

    /**
     * Set the current hour.
     */
    public void setCurrentHour(int currentHour) {
        this.mCurrentHour = currentHour;
        updateHourDisplay();
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(int currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentSecond(int currentSecond) {
        this.mCurrentSecond = currentSecond;
        updateSecondDisplay();
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * 
     * @param onTimeChangedListener
     *            the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private void updateHourDisplay() {
        int currentHour = mCurrentHour;
        if (!mIs24HourView) {
            // convert [0,23] ordinal to wall clock display
            if (currentHour > 12)
                currentHour -= 12;
            else if (currentHour == 0)
                currentHour = 12;
        }
        mHourPicker.setCurrent(currentHour);
        mIsAm = mCurrentHour < 12;
        mAmPmButton.setText(mIsAm ? mAmText : mPmText);
        onTimeChanged();
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setCurrent(mCurrentMinute);
        onTimeChanged();
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateSecondDisplay() {
        mSecondPicker.setCurrent(mCurrentSecond);
        onTimeChanged();
    }

    @Override
    public void onChanged(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
        case R.id.hour:
            setCurrentHour(newVal);
            break;
        case R.id.minute:
            setCurrentMinute(newVal);
            break;
        case R.id.second:
            setCurrentSecond(newVal);
            break;
        default:
            throw new IllegalStateException();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentHour, mCurrentMinute, mCurrentSecond);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mCurrentHour = ss.getHour();
        mCurrentMinute = ss.getMinute();
        mCurrentSecond = ss.getSecound();
        updateSpinners();
    }

    private void updateSpinners() {
        setCurrentHour(mCurrentHour);
        setCurrentMinute(mCurrentMinute);
        setCurrentSecond(mCurrentSecond);
    }

    private static class SavedState extends BaseSavedState {

        private final int mCurrentHour;
        private final int mCurrentMinute;
        private final int mCurrentSecound;

        /**
         * Constructor called from {@link DatePicker#onSaveInstanceState()}
         */
        private SavedState(Parcelable superState, int hour, int minute, int secound) {
            super(superState);
            mCurrentHour = hour;
            mCurrentMinute = minute;
            mCurrentSecound = secound;
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            mCurrentHour = in.readInt();
            mCurrentMinute = in.readInt();
            mCurrentSecound = in.readInt();
        }

        public int getHour() {
            return mCurrentHour;
        }

        public int getMinute() {
            return mCurrentMinute;
        }

        public int getSecound() {
            return mCurrentSecound;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mCurrentHour);
            dest.writeInt(mCurrentMinute);
            dest.writeInt(mCurrentSecound);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
