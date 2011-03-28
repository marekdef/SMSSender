package net.retsat1.starlab.smssender;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsCursorAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class ScheduledSmsList extends Activity {
    private static final String TAG = ScheduledSmsList.class.getSimpleName();

    private ListView smsListView;
    private SmsCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        smsListView = (ListView) findViewById(R.id.smsList);
        Cursor c = managedQuery(SmsMessage.CONTENT_URI, null, null, null, null);
        String[] valuePosition = { SmsMessage.RECEIVER, SmsMessage.MESSAGE, SmsMessage.MESSAGE_STATUS };
        int[] uiPosition = { R.id.numberText, R.id.messageText, R.id.stat };
        adapter = new SmsCursorAdapter(this, R.layout.list_item, c, valuePosition, uiPosition);
        smsListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_sms:
            startActivity(new Intent(this, ScheduleNewSms.class));
            return true;
        case R.id.delete_checked:
            adapter.deleteAllCheckedItems();
            return true;
        case R.id.select_all:
            adapter.selectAllItems();
            return true;

        }
        return super.onMenuItemSelected(featureId, item);
    }
}