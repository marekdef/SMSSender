package net.retsat1.starlab.smssender.exception;

import java.util.HashMap;
import java.util.Map;

import net.retsat1.starlab.smssender.R;
import android.content.Context;
import android.util.Log;

/**
 * This is exception which can't be resolved, We can only notifi user that this
 * exception is. Exception should has international messages
 * 
 * @author mario
 * 
 */
public class GUIException extends Exception {
    // This be more
    public final static int ERROR_DELIVERY_STATUS_GENERAL = -110;
    // Mapping between error code and messages
    private final static Map<Integer, Integer> i18nErrorMapping;
    private static final String TAG = "GUIException";
    public static final int ERROR_DELIVERY_EMPTY_MESSAGE = -111;
    static {
        i18nErrorMapping = new HashMap<Integer, Integer>();
        i18nErrorMapping.put(ERROR_DELIVERY_STATUS_GENERAL, R.string.dialog_alert_title);
        i18nErrorMapping.put(ERROR_DELIVERY_EMPTY_MESSAGE, R.string.dialog_empty_message);
    }

    private int bugID;
    private Context context;

    public GUIException(Context context, int bugID) {
        this.context = context;
        this.bugID = bugID;
    }

    @Override
    public String getMessage() {
        String message = context.getApplicationContext().getString(i18nErrorMapping.get(bugID));
        Log.e(TAG, message);
        return message;
    }
}
