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
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        if (logOn) {
            Log.d(TAG, message);
        }
    }
}
