package net.retsat1.starlab.smssender;

import java.util.Timer;
import java.util.TimerTask;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsCursorAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ScheduledSmsList extends ListActivity implements OnClickListener {
    private static final String TAG = ScheduledSmsList.class.getSimpleName();
    private SmsCursorAdapter adapter;
    private Timer timer = null;
    private Button newSmsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_list);
        // Tell the list view which view to display when the list is empty
        newSmsButton = (Button) findViewById(R.id.new_sms);
        newSmsButton.setOnClickListener(this);
        getListView().setEmptyView(newSmsButton);
        Cursor c = managedQuery(SmsMessage.CONTENT_URI, null, null, null, null);
        adapter = new SmsCursorAdapter(this, R.layout.list_item, c);
        getListView().setAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onResume() {
        refreshListForEverySecound();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        timer.cancel();
        timer.purge();
        timer = null;
    }

    private void refreshListForEverySecound() {
        timer = new Timer();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        timer.schedule(tt, 1000, 1000);

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick position" + position);
        String selection = l.getItemAtPosition(position).toString();
        Log.d(TAG, "selection " + selection);
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.new_sms:
            startActivity(new Intent(this, ScheduleNewSms.class));
            break;

        default:
            throw new IllegalStateException("Not support. Pressed item is: " + v.getId());
        }

    }
}