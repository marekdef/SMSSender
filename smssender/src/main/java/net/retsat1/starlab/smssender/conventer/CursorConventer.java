package net.retsat1.starlab.smssender.conventer;

import android.database.Cursor;

public interface CursorConventer<T> {

    public T convert(Cursor cursor);
}
