package net.retsat1.starlab.smssender.conventer;

import android.database.Cursor;

/**
 * Define converter, which converting Cursor to DTO class
 * 
 * @author mario
 * 
 * @param <T>
 */
public interface CursorConventer<T> {

    /**
     * Convert cursor to DTO Class
     * 
     * @param cursor
     * @return
     */
    public T convert(Cursor cursor);
}
