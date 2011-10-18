package net.retsat1.starlab.smssender;

import java.util.Timer;
import java.util.TimerTask;

import net.retsat1.starlab.smssender.dto.SmsMessage;
import net.retsat1.starlab.smssender.ui.adapter.SmsCursorAdapter;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ScheduledSmsList extends ListActivity implements OnClickListener, OnItemClickListener, OnLongClickListener {
    private static final String TAG = ScheduledSmsList.class.getSimpleName();
    private static final int DIALOG_INFO_ID = 1;

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
        getListView().setOnItemClickListener(this);
        registerForContextMenu(getListView());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            return false;
        }
        MyLog.d(TAG, "info.position = " + info.position + " getListAdapter() " + adapter);
        MyLog.d(TAG, "item " + adapter.getItemId(info.position));
        switch (item.getItemId()) {
        case R.id.edit_context_menu:
            runEditSmsModeActivity(info.position);
            return true;
        case R.id.delete_context_menu:
            adapter.delete(info.position);
            return true;
        case R.id.copy_context_menu:
            runCopySmsModeActivity(info.position);
            return true;
        }
        return false;
    }

    private void runCopySmsModeActivity(int position) {
        int smsid = adapter.getSmsIDByPosition(position);
        Intent intent = new Intent(this, ScheduleNewSms.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ScheduleNewSms.SCREEN_MODE, ScheduleNewSms.SCREEN_MODE_COPY);
        bundle.putInt(SmsMessage.SMS_ID, smsid);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void runEditSmsModeActivity(int position) {
        int smsid = adapter.getSmsIDByPosition(position);
        Intent intent = new Intent(this, ScheduleNewSms.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ScheduleNewSms.SCREEN_MODE, ScheduleNewSms.SCREEN_MODE_EDIT);
        bundle.putInt(SmsMessage.SMS_ID, smsid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        refreshListForEverySecound();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.d(TAG, "onPause ");
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
        timer.schedule(tt, 10000, 10000);

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
        case R.id.delete_all_old:
            adapter.deleteOldTasks();
            return true;
        case R.id.about:
            showDialog(DIALOG_INFO_ID);
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private Dialog createInfoDialog() {

        Dialog dialog = new Dialog(ScheduledSmsList.this);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(getResources().getString(R.string.app_name));
        ImageView image1 = (ImageView) dialog.findViewById(R.id.image1);
        ImageView image2 = (ImageView) dialog.findViewById(R.id.image2);
        ImageView image3 = (ImageView) dialog.findViewById(R.id.image3);
        image1.setImageResource(R.drawable.icon);
        image2.setImageResource(R.drawable.marek_s);
        image3.setImageResource(R.drawable.mario);
        dialog.setOwnerActivity(this);

        return dialog;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        MyLog.d(TAG, "onCreateDialog " + id);
        switch (id) {
        case DIALOG_INFO_ID:
            dialog = createInfoDialog();
            break;
        default:
            dialog = null;
            break;
        }
        return dialog;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        MyLog.d(TAG, "onListItemClick position" + position);
        String selection = l.getItemAtPosition(position).toString();
        MyLog.d(TAG, "selection " + selection);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyLog.d(TAG, "onItemClick " + id + " position " + position);
    }

    @Override
    public boolean onLongClick(View v) {
        MyLog.d(TAG, "onItemClick " + v);
        return true;
    }
}
