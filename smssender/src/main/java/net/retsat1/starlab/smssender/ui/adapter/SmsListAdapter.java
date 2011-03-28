package net.retsat1.starlab.smssender.ui.adapter;

import net.retsat1.starlab.smssender.R;
import net.retsat1.starlab.smssender.dto.SmsMessage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;

public class SmsListAdapter extends ArrayAdapter<SmsMessage> {

    private Context context;

    public SmsListAdapter(Context context, int textViewResourceId) {
        super(context, R.layout.list_item);
        this.context = context;
    }

    // static to save the reference to the outer class and to avoid access to
    // any members of the containing class
    static class ViewHolder {
        public TextView number;
        public TextView message;
        public Chronometer timeToGo;
        public CheckBox checked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder will buffer the assess to the individual fields of the row
        // layout

        ViewHolder holder;
        // Recycle existing view if passed as parameter
        // This will save memory and time on Android
        // This only works if the base layout for all classes are the same
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null, true);
            holder = new ViewHolder();
            holder.checked = (CheckBox) rowView.findViewById(R.id.checked);

            // holder.timeToGo = (Chronometer)
            // rowView.findViewById(R.id.how_long_chronometer);
            holder.number = (TextView) rowView.findViewById(R.id.numberText);
            holder.message = (TextView) rowView.findViewById(R.id.messageText);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        SmsMessage item = getItem(position);
        holder.number.setText(item.getReceiver());
        holder.message.setText(item.getMessage());
        if (holder.checked.isChecked()) {
            holder.number.setText("c");
        } else {
            holder.number.setText("nc");
        }
        return rowView;
    }

}
