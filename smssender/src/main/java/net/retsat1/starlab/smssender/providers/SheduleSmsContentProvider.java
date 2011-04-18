package net.retsat1.starlab.smssender.providers;

import java.util.HashMap;
import java.util.Map;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Sms content provider to provide information about sms
 * 
 * @author mario
 * 
 */
public class SheduleSmsContentProvider extends ContentProvider {

    public static final String TAG = "SheduleSmsContentProvider";

    private static final String DATABASE_NAME = "smsshedule.db";

    private static final int DATABASE_VERSION = 5;

    public static final String PROVIDER_NAME = "net.retsat1.starlab.smssender.providers.SheduleSmsContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/sms");

    private static final UriMatcher sUriMatcher;

    private static final String SMS_TABLE_NAME = "sms";

    private static final int SMS_CODE = 1;
    private static final Map<String, String> notesProjectionMap;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(PROVIDER_NAME, SMS_TABLE_NAME, SMS_CODE);

        notesProjectionMap = new HashMap<String, String>();

        notesProjectionMap.put(SmsMessage.SMS_ID, SmsMessage.SMS_ID);
        notesProjectionMap.put(SmsMessage.RECEIVER, SmsMessage.RECEIVER);
        notesProjectionMap.put(SmsMessage.MESSAGE, SmsMessage.MESSAGE);
        notesProjectionMap.put(SmsMessage.MESSAGE_STATUS, SmsMessage.MESSAGE_STATUS);
        notesProjectionMap.put(SmsMessage.DELIVERY_STATUS, SmsMessage.DELIVERY_STATUS);
        notesProjectionMap.put(SmsMessage.STATUS_DATE, SmsMessage.STATUS_DATE);
        notesProjectionMap.put(SmsMessage.SETUP_DATE, SmsMessage.SETUP_DATE);
        notesProjectionMap.put(SmsMessage.DELIVERY_DATE, SmsMessage.DELIVERY_DATE);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            MyLog.d(TAG, "onCreate database");
            database.execSQL("CREATE TABLE " + SMS_TABLE_NAME + " (" + SmsMessage.SMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SmsMessage.RECEIVER
                    + " VARCHAR(255)," + SmsMessage.MESSAGE + " LONGTEXT," + SmsMessage.MESSAGE_STATUS + " INTEGER," + SmsMessage.DELIVERY_STATUS + " INTEGER,"
                    + SmsMessage.SETUP_DATE + " INTEGER," + SmsMessage.STATUS_DATE + " INTEGER," + SmsMessage.DELIVERY_DATE + " INTEGER" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            MyLog.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data " + db.isReadOnly()
                    + " open=" + db.isOpen());

            db.execSQL("DROP TABLE IF EXISTS " + SMS_TABLE_NAME);
            onCreate(db);
        }

    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case SMS_CODE:
            count = db.delete(SMS_TABLE_NAME, where, whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case SMS_CODE:
            return SmsMessage.CONTENT_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != SMS_CODE) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowId = db.insert(SMS_TABLE_NAME, SmsMessage.MESSAGE, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(SmsMessage.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
        case SMS_CODE:
            qb.setTables(SMS_TABLE_NAME);
            qb.setProjectionMap(notesProjectionMap);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case SMS_CODE:
            count = db.update(SMS_TABLE_NAME, values, where, whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

}
