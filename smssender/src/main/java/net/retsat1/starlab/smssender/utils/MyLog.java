package net.retsat1.starlab.smssender.utils;

import android.util.Log;

public class MyLog {

    public static boolean logOn = true;

    public static void d(String TAG, String message) {
        if (logOn) {
            Log.d(TAG, message);
        }
    }

    public static void v(String TAG, String message) {
        if (logOn) {
            Log.v(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        if (logOn) {
            Log.e(TAG, message);
        }
    }

    public static void e(String TAG, String message, Throwable e) {
        if (logOn) {
            Log.e(TAG, message, e);
        }
    }

    public static void i(String TAG, String message) {

        if (logOn) {
            Log.i(TAG, message);
        }
    }

    public static void w(String TAG, String message) {
        if (logOn) {
            Log.w(TAG, message);
        }

    }
}
